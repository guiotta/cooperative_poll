package com.otta.cooperative.poll.meeting.extractor;

import java.util.Optional;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PollIdQuartzContextoExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollIdQuartzContextoExtractor.class);

    public static final String POLL_ID_KEY = "pollId";

    public Optional<Long> extract(JobExecutionContext context) {
        Optional<JobDetail> optionalJobDetail = Optional.ofNullable(context.getJobDetail());

        if (optionalJobDetail.isPresent()) {
            Optional<JobDataMap> optionalJobDataMap = Optional.ofNullable(optionalJobDetail.get().getJobDataMap());

            if (optionalJobDataMap.isPresent()) {
                Long pollId = optionalJobDataMap.get().getLong(POLL_ID_KEY);
                LOGGER.info("Extracted PollId {} from Context.", pollId);

                return Optional.ofNullable(pollId);
            }
        }

        LOGGER.warn("Could not find any PollId to extract in Context.");
        return Optional.empty();
    }
}
