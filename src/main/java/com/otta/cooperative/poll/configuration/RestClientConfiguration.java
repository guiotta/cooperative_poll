package com.otta.cooperative.poll.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.otta.cooperative.poll.vote.client.rest.StatusClient;
import com.otta.cooperative.poll.vote.client.rest.StatusControllerFeignClientBuilder;

@Configuration
public class RestClientConfiguration {

    @Bean
    public StatusControllerFeignClientBuilder statusControllerFeignClientBuilder() {
        return new StatusControllerFeignClientBuilder();
    }

    @Bean
    public StatusClient statusClient(StatusControllerFeignClientBuilder statusControllerFeignClientBuilder) {
        return statusControllerFeignClientBuilder.build();
    }
}
