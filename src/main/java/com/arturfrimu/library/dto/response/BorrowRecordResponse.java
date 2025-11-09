package com.arturfrimu.library.dto.response;

import java.time.Instant;
import java.util.UUID;

public record BorrowRecordResponse(
        UUID id,
        UUID userId,
        UUID bookId,
        Instant borrowDate,
        Instant returnDate,
        Boolean active
) {
}

