package com.arturfrimu.library.spec.integration.controller;

import com.arturfrimu.library.spec.integration.LifecycleSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest extends LifecycleSpecification {

    @Test
    @Sql({"/sql/insert_test_countries.sql", "/sql/insert_test_authors.sql"})
    void shouldCreateNewBook() {
        var request = dtoCreator.buildCreateBookRequest();
        var response = bookApiTestService.createBook(request);

        assertNotNull(response, "Response must not be null");
        assertNotNull(response.id(), "Book ID must not be null");
        assertEquals(request.title(), response.title(), "Title must match");
        assertEquals(request.isbn(), response.isbn(), "ISBN must match");
        assertEquals(request.publishedYear(), response.publishedYear(), "Published year must match");
        assertEquals(request.authorId(), response.authorId(), "Author ID must match");
        assertTrue(response.active(), "Book must be active");
        assertNotNull(response.created(), "Created date must not be null");
        assertNotNull(response.updated(), "Updated date must not be null");
    }

    @Test
    @Sql({"/sql/insert_test_countries.sql", "/sql/insert_test_authors.sql", "/sql/insert_test_books.sql"})
    void shouldFindBooks() {
        var response = bookApiTestService.findBooks(0, 100);

        assertNotNull(response, "Response must not be null");
        assertNotNull(response.getContent(), "Content must not be null");
        assertTrue(response.getTotalElements() >= 3, "Total elements must be at least 3");
        
        var testBook1Id = UUID.fromString("33333333-3333-3333-3333-333333333333");
        var testBook2Id = UUID.fromString("44444444-4444-4444-4444-444444444444");
        var testBook3Id = UUID.fromString("55555555-5555-5555-5555-555555555555");
        
        assertTrue(response.getContent().stream().anyMatch(book -> book.id().equals(testBook1Id)), "Test Book 1 must be present");
        assertTrue(response.getContent().stream().anyMatch(book -> book.id().equals(testBook2Id)), "Test Book 2 must be present");
        assertTrue(response.getContent().stream().anyMatch(book -> book.id().equals(testBook3Id)), "Test Book 3 must be present");
    }
}