package com.barbosacode.delivery.msdelivery.tracking.api.dto.response;

import java.util.UUID;

public record ItemResponse(UUID id,
                           String name,
                           String description,
                           Integer quantity) {
}
