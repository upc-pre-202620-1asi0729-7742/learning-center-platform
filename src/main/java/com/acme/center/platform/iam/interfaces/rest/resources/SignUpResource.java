package com.acme.center.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * Resource received to register a new IAM user.
 */
@Schema(
    name = "SignUpRequest",
    description = "User sign-up request with credentials and roles",
    example = "{\"username\": \"john.doe\", \"password\": \"SecurePass123!\", \"roles\": [\"ROLE_USER\"]}"
)
public record SignUpResource(
    @Schema(
        description = "Desired username",
        example = "john.doe",
        minLength = 3,
        maxLength = 50
    )
    String username,

    @Schema(
        description = "User password (minimum 8 characters)",
        example = "SecurePass123!",
        minLength = 8,
        maxLength = 255
    )
    String password,

    @Schema(
        description = "Roles to assign to the user",
        example = "[\"ROLE_USER\"]"
    )
    List<String> roles
) {
}
