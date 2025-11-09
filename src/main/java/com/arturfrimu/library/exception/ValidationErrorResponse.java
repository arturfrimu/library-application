package com.arturfrimu.library.exception;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
        Instant timestamp,
        Integer status,
        String error,
        Map<String, String> errors
) {
}

