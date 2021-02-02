package com.otta.cooperative.poll.vote.client.rest;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public class StatusControllerFeignClientBuilder {
    private static final String URL = "https://user-info.herokuapp.com/users";

    public StatusClient build() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(StatusClient.class, URL);
    }
}
