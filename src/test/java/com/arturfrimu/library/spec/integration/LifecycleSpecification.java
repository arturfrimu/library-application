package com.arturfrimu.library.spec.integration;

import com.arturfrimu.library.LibraryApplication;
import com.arturfrimu.library.spec.support.TestSupportConfig;
import com.arturfrimu.library.testcontainer.container.Containers;
import com.arturfrimu.library.testcontainer.container.PostgresTestContainer;
import com.arturfrimu.library.testcontainer.container.WireMockTestContainer;
import com.arturfrimu.library.testcontainer.data.DtoCreator;
import com.arturfrimu.library.testcontainer.service.BookApiTestService;
import com.arturfrimu.library.testcontainer.service.BorrowApiTestService;
import com.arturfrimu.library.testcontainer.service.UserApiTestService;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LibraryApplication.class, TestSupportConfig.class})
public abstract class LifecycleSpecification {

    @Autowired
    protected DtoCreator dtoCreator;
    @Autowired
    protected BookApiTestService bookApiTestService;
    @Autowired
    protected UserApiTestService userApiTestService;
    @Autowired
    protected BorrowApiTestService borrowApiTestService;

    static {
        Containers.run();
    }

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", () -> PostgresTestContainer.postgresTestContainer.getJdbcUrl());
        r.add("spring.datasource.username", () -> PostgresTestContainer.postgresTestContainer.getUsername());
        r.add("spring.datasource.password", () -> PostgresTestContainer.postgresTestContainer.getPassword());

        final String wireMockBase = "http://" +
                WireMockTestContainer.wireMockContainer.getHost() + ":" +
                WireMockTestContainer.wireMockContainer.getFirstMappedPort();

        r.add("application.person.url", () -> wireMockBase);
    }
}