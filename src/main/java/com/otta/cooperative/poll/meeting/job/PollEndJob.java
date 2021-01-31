package com.otta.cooperative.poll.meeting.job;

import java.util.Optional;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.repository.PollRepository;

public class PollEndJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollEndJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Optional<Long> optionalPollId = this.extractPollIdFromJobExecutionContext(context);
        Optional<Object> optionalRepository = this.extractPollRepositoryFromJobExecutionContext(context);

        if (optionalPollId.isPresent() && optionalRepository.isPresent()) {
            Long pollId = optionalPollId.get();
            PollRepository pollRepository = (PollRepository) optionalRepository.get();
            Optional<PollEntity> optionalEntity = pollRepository.findById(pollId);

            if (optionalEntity.isPresent()) {
                PollEntity entity = optionalEntity.get();
                entity.setEnabled(false);

                LOGGER.info("Updating PollEntity with id {} to disabled.", pollId);
                pollRepository.save(entity);
            } else {
                LOGGER.error("Could not find any PollEntity with Id {}.", pollId);
            }
        } else {
            LOGGER.error("Could not extract any PollId from JobExecutionContext to finalize Poll.");
        }
    }

    private Optional<Long> extractPollIdFromJobExecutionContext(JobExecutionContext context) {
        Optional<JobDetail> optionalJobDetail = Optional.ofNullable(context.getJobDetail());

        if (optionalJobDetail.isPresent()) {
            Optional<JobDataMap> optionalJobDataMap = Optional.ofNullable(optionalJobDetail.get().getJobDataMap());

            if (optionalJobDataMap.isPresent()) {
                return  Optional.ofNullable(optionalJobDataMap.get().getLong("pollId"));
            }
        }

        return Optional.empty();
    }

    private Optional<Object> extractPollRepositoryFromJobExecutionContext(JobExecutionContext context) {
        Optional<JobDetail> optionalJobDetail = Optional.ofNullable(context.getJobDetail());

        if (optionalJobDetail.isPresent()) {
            Optional<JobDataMap> optionalJobDataMap = Optional.ofNullable(optionalJobDetail.get().getJobDataMap());

            if (optionalJobDataMap.isPresent()) {
                return  Optional.ofNullable(optionalJobDataMap.get().get("pollRepository"));
            }
        }

        return Optional.empty();
    }
}
