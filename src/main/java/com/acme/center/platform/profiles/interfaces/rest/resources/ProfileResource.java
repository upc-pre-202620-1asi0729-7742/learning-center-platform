package com.acme.center.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a profile.
 */
@Schema(
    name = "ProfileResponse",
    description = "Profile information response",
    example = "{\"id\": 1, \"fullName\": \"John Doe\", \"email\": \"john.doe@example.com\", \"streetAddress\": \"123 Main St, Apt 4, Springfield, 12345, USA\"}"
)
public record ProfileResource(
    @Schema(description = "Profile unique identifier", example = "1")
    Long id,

    @Schema(description = "Profile full name", example = "John Doe")
    String fullName,

    @Schema(description = "Profile email address", example = "john.doe@example.com")
    String email,

    @Schema(description = "Complete street address", example = "123 Main St, Apt 4, Springfield, 12345, USA")
    String streetAddress
) {
}
