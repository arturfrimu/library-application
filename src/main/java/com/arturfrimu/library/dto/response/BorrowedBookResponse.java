package com.arturfrimu.library.dto.response;

import java.time.Instant;
import java.util.UUID;

public record BorrowedBookResponse(
        UUID borrowRecordId,
        Instant borrowDate,
        UUID userId,
        String userEmail,
        String userFirstName,
        String userLastName,
        UUID bookId,
        String bookTitle,
        String bookIsbn,
        Integer bookPublishedYear,
        UUID authorId,
        String authorName
) {
}

