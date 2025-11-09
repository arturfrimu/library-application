package com.arturfrimu.library.testcontainer.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public abstract class BaseApiTestService {

    protected final Environment env;
    protected final RestTemplate restTemplate;

    protected String baseUrl() {
        Integer port = env.getProperty("local.server.port", Integer.class);
        if (port == null || port == 0) {
            port = env.getProperty("server.port", Integer.class, 8092);
        }
        return "http://localhost:" + port + "/api";
    }
}