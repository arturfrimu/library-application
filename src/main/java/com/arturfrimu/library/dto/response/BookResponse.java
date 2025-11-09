package com.arturfrimu.library.dto.response;

import java.time.Instant;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        UUID authorId,
        String authorName,
        String isbn,
        Integer publishedYear,
        Boolean active,
        Instant created,
        Instant updated
) {
}

