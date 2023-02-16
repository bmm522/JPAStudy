package com.personal.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class StaticStore {

    private static final HttpHeaders headers = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final StaticStore staticStore = new StaticStore();

    private StaticStore(){
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public StaticStore getInstance(){
        return staticStore;
    }

    public static HttpHeaders getHeaders(){
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
