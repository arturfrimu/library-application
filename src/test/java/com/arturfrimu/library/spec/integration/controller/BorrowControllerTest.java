package com.arturfrimu.library.spec.integration.controller;

import com.arturfrimu.library.dto.request.BorrowBookRequest;
import com.arturfrimu.library.dto.request.ReturnBookRequest;
import com.arturfrimu.library.spec.integration.LifecycleSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BorrowControllerTest extends LifecycleSpecification {

    @Test
    @Sql({"/sql/insert_test_countries.sql", "/sql/insert_test_authors.sql", "/sql/insert_test_addresses.sql", "/sql/insert_test_users.sql", "/sql/insert_test_books.sql"})
    void shouldBorrowBook() {
        var userId = UUID.fromString("88888888-8888-8888-8888-888888888888");
        var bookId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        var request = new BorrowBookRequest(userId, bookId);

        var borrowRecordId = borrowApiTestService.borrowBook(request);

        assertNotNull(borrowRecordId, "Borrow record ID must not be null");
    }

    @Test
    @Sql({"/sql/insert_test_countries.sql", "/sql/insert_test_authors.sql", "/sql/insert_test_addresses.sql", "/sql/insert_test_users.sql", "/sql/insert_test_books.sql"})
    void shouldReturnBook() {
        var userId = UUID.fromString("99999999-9999-9999-9999-999999999999");
        var bookId = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
        var borrowRequest = new BorrowBookRequest(userId, bookId);
        var borrowRecordId = borrowApiTestService.borrowBook(borrowRequest);

        assertNotNull(borrowRecordId, "Borrow record ID must not be null");

        var returnRequest = new ReturnBookRequest(borrowRecordId);
        var returnedRecordId = borrowApiTestService.returnBook(returnRequest);

        assertNotNull(returnedRecordId, "Returned record ID must not be null");
        assertEquals(borrowRecordId, returnedRecordId, "Returned record ID must match borrow record ID");
    }

    @Test
    @Sql({"/sql/insert_test_countries.sql", "/sql/insert_test_authors.sql", "/sql/insert_test_addresses.sql", "/sql/insert_test_users.sql", "/sql/insert_test_books.sql"})
    void shouldGetBorrowedBooks() {
        var userId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        var bookId1 = UUID.fromString("33333333-3333-3333-3333-333333333333");
        var bookId2 = UUID.fromString("44444444-4444-4444-4444-444444444444");

        var borrowRequest1 = new BorrowBookRequest(userId, bookId1);
        var borrowRequest2 = new BorrowBookRequest(userId, bookId2);

        var borrowRecordId1 = borrowApiTestService.borrowBook(borrowRequest1);
        var borrowRecordId2 = borrowApiTestService.borrowBook(borrowRequest2);

        assertNotNull(borrowRecordId1, "Borrow record ID 1 must not be null");
        assertNotNull(borrowRecordId2, "Borrow record ID 2 must not be null");

        var borrowedBooks = borrowApiTestService.getBorrowedBooks(userId);

        assertNotNull(borrowedBooks, "Borrowed books list must not be null");
        assertTrue(borrowedBooks.size() >= 2, "Borrowed books list must contain at least 2 books");
        assertTrue(borrowedBooks.stream().anyMatch(book -> book.borrowRecordId().equals(borrowRecordId1)), "Borrow record 1 must be present");
        assertTrue(borrowedBooks.stream().anyMatch(book -> book.borrowRecordId().equals(borrowRecordId2)), "Borrow record 2 must be present");
        assertTrue(borrowedBooks.stream().allMatch(book -> book.userId().equals(userId)), "All books must belong to the same user");
    }
}