package com.arturfrimu.library.testcontainer.container;

import com.arturfrimu.library.testcontainer.util.Setting;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class PostgresTestContainer {

    public static final PostgreSQLContainer<?> postgresTestContainer;

    static {
        postgresTestContainer = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("library")
                .withUsername("postgres")
                .withPassword("postgres")
                .withNetwork(Setting.GLOBAL_NETWORK)
                .withNetworkAliases("postgres");
    }

    public static void logConnectionDetails() {
        if (postgresTestContainer.isRunning()) {
            log.info("=== PostgreSQL TestContainer Connection Details ===");
            log.info("JDBC URL: {}", postgresTestContainer.getJdbcUrl());
            log.info("Username: {}", postgresTestContainer.getUsername());
            log.info("Password: {}", postgresTestContainer.getPassword());
            log.info("Database Name: {}", postgresTestContainer.getDatabaseName());
            log.info("Host: {}", postgresTestContainer.getHost());
            log.info("Port: {}", postgresTestContainer.getFirstMappedPort());
            log.info("==================================================");
        }
    }
}