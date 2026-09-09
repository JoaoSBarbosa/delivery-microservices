package com.barbosacode.delivery.msdelivery.tracking.api.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiError(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldError> errors
) {

    public record FieldError(
            String field,
            String message
    ) {
    }
}
