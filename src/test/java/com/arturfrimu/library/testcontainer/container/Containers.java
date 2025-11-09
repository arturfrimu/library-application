package com.arturfrimu.library.testcontainer.container;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@UtilityClass
public class Containers {

    public PostgreSQLContainer<?> postgres = PostgresTestContainer.postgresTestContainer;
    public GenericContainer<?> wireMockContainer = WireMockTestContainer.wireMockContainer;

    public void run() {
        log.info("Starting test containers...");
        postgres.start();
        PostgresTestContainer.logConnectionDetails();
        wireMockContainer.start();
        log.info("All test containers started successfully");
    }
}