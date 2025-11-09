package com.arturfrimu.library.testcontainer.container;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class WireMockTestContainer {

    public static final GenericContainer<?> wireMockContainer;

    static {
        wireMockContainer = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:3.13.0"))
                .withExposedPorts(8080);
    }
}