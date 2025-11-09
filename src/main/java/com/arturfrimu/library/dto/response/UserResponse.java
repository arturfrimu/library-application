package com.arturfrimu.library.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        UUID addressId,
        Boolean active,
        Instant created,
        Instant updated
) {}