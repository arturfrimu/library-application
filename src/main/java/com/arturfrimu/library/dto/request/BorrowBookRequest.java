package com.arturfrimu.library.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BorrowBookRequest(
        @NotNull(message = "User ID is required")
        UUID userId,
        @NotNull(message = "Book ID is required")
        UUID bookId
) {
}

