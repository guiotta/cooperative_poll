package com.otta.cooperative.poll.vote.client.rest;

import com.otta.cooperative.poll.vote.client.model.StatusResource;

import feign.Param;
import feign.RequestLine;

public interface StatusClient {
    @RequestLine("GET /{document}")
    StatusResource findByDocument(@Param("document") String document);
}
