package com.trisha.bff.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class MidiaClient {

    private final RestClient midiaRestClient;

    public MidiaClient(@Qualifier("midiaRestClient") RestClient midiaRestClient) {
        this.midiaRestClient = midiaRestClient;
    }
}
