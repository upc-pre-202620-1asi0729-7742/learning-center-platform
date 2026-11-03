package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Update course resource.
 */
@Schema(
    name = "UpdateCourseRequest",
    description = "Request payload for updating an existing course",
    example = "{\"title\": \"Advanced Java\", \"description\": \"Learn advanced Java concepts\"}"
)
public record UpdateCourseResource(
    @Schema(
        description = "Course title",
        example = "Advanced Java",
        minLength = 1,
        maxLength = 255
    )
    String title,

    @Schema(
        description = "Course description",
        example = "Learn advanced Java concepts",
        minLength = 1,
        maxLength = 2000
    )
    String description
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if the title or description is null or blank.
     */
    public UpdateCourseResource {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
    }
}
