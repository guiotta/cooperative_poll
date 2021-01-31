package com.otta.cooperative.poll.meeting.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.job.PollEndJob;
import com.otta.cooperative.poll.meeting.mapper.MeetingEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.MeetingOutputMapper;
import com.otta.cooperative.poll.meeting.mapper.PollEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.PollOutputMapper;
import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;
import com.otta.cooperative.poll.meeting.repository.PollRepository;

@Service
public class MeetingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingService.class);

    
    private final MeetingRepository meetingRepository;
    private final PollRepository pollRepository;
    private final MeetingEntityMapper meetingEntityMapper;
    private final MeetingOutputMapper meetingOutputMapper;
    private final PollEntityMapper pollEntityMapper;
    private final PollOutputMapper pollOutputMapper;

    public MeetingService(MeetingRepository meetingRepository, PollRepository pollRepository,
            MeetingEntityMapper meetingEntityMapper, MeetingOutputMapper meetingOutputMapper,
            PollEntityMapper pollEntityMapper, PollOutputMapper pollOutputMapper) {
        this.meetingRepository = meetingRepository;
        this.pollRepository = pollRepository;
        this.meetingEntityMapper = meetingEntityMapper;
        this.meetingOutputMapper = meetingOutputMapper;
        this.pollEntityMapper = pollEntityMapper;
        this.pollOutputMapper = pollOutputMapper;
    }

    public MeetingOutput save(MeetingInput input) {
        MeetingEntity entity = meetingEntityMapper.map(input);
        entity = meetingRepository.save(entity);

        return meetingOutputMapper.map(entity);
    }

    public Collection<MeetingOutput> findAll() {
        Collection<MeetingEntity> entities = meetingRepository.findAll();

        return entities.stream()
                .map(entity -> meetingOutputMapper.map(entity))
                .collect(Collectors.toList());
    }

    public PollOutput save(PollInput input) throws SchedulerException {
        Optional<MeetingEntity> optionalMeeting = meetingRepository.findById(input.getMeetingId());

        if (optionalMeeting.isPresent()) {
            MeetingEntity meetingEntity = optionalMeeting.get();

            if (meetingEntity.getPoll() == null) {
                LOGGER.info("Adding a Poll to Meeting with Id {}.", meetingEntity.getId());

                PollEntity pollEntity = pollEntityMapper.map(input, meetingEntity);
                meetingEntity.setPoll(pollEntity);
                meetingEntity = meetingRepository.save(meetingEntity);

                this.scheduleJob(pollEntity.getClose(), pollEntity.getId());

                return pollOutputMapper.map(meetingEntity.getPoll());
            }
            LOGGER.debug("Meeting with Id {} already has a poll. Poll Id {}.", meetingEntity.getId(), meetingEntity.getPoll().getId());
            throw new IllegalStateException(String.format("Meeting already has a Poll associated."));
        }
        LOGGER.debug("Could not find any Meeting with id {}.", input.getMeetingId());
        throw new IllegalArgumentException(String.format("Could not find a Meeting to add a Poll."));
    }

    private void scheduleJob(LocalDateTime executeDateTime, Long pollId) throws SchedulerException {
        //JobDataMap dataMap = new JobDataMap();
        //dataMap.put("pollId", pollId);

        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        JobDetail job = JobBuilder.newJob(PollEndJob.class).build();
        job.getJobDataMap().put("pollId", pollId);
        job.getJobDataMap().put("pollRepository", pollRepository);
        Trigger trigger = TriggerBuilder.newTrigger()
                                        //.usingJobData(dataMap)
                                        .startAt(Timestamp.valueOf(executeDateTime))
                                        //.withSchedule(SimpleScheduleBuilder.simpleSchedule())
                                        .build();
        scheduler.scheduleJob(job, trigger);
    }
}
