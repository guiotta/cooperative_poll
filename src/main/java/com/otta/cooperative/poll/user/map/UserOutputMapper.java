package com.otta.cooperative.poll.user.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.model.UserOutput;

@Component
public class UserOutputMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserOutputMapper.class);

    public UserOutput map(UserEntity entity) {
        UserOutput output = new UserOutput(entity.getId(), entity.getDocument(), entity.getName());
        LOGGER.info("Mapping UserEntity {} to UserOutput {}.", entity, output);

        return output;
    }
}
