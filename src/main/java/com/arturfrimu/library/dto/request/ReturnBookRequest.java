package com.arturfrimu.library.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReturnBookRequest(
        @NotNull(message = "Borrow record ID is required")
        UUID borrowRecordId
) {
}

