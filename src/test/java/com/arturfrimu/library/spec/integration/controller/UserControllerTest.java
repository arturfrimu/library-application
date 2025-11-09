package com.arturfrimu.library.spec.integration.controller;

import com.arturfrimu.library.spec.integration.LifecycleSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest extends LifecycleSpecification {

    @Test
    @Sql({"/sql/insert_test_countries.sql", "/sql/insert_test_addresses.sql", "/sql/insert_test_users.sql"})
    void shouldFindUsers() {
        var response = userApiTestService.findUsers(0, 100);

        assertNotNull(response, "Response must not be null");
        assertNotNull(response.getContent(), "Content must not be null");
        assertTrue(response.getTotalElements() >= 3, "Total elements must be at least 3");

        var testUser1Id = UUID.fromString("88888888-8888-8888-8888-888888888888");
        var testUser2Id = UUID.fromString("99999999-9999-9999-9999-999999999999");
        var testUser3Id = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        assertTrue(response.getContent().stream().anyMatch(user -> user.id().equals(testUser1Id)), "Test User 1 must be present");
        assertTrue(response.getContent().stream().anyMatch(user -> user.id().equals(testUser2Id)), "Test User 2 must be present");
        assertTrue(response.getContent().stream().anyMatch(user -> user.id().equals(testUser3Id)), "Test User 3 must be present");
    }
}