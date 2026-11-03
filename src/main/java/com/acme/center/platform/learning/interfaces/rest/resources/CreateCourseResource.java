package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create course resource.
 */
@Schema(
    name = "CreateCourseRequest",
    description = "Request payload for creating a new course",
    example = "{\"title\": \"Introduction to Java\", \"description\": \"Learn Java fundamentals and best practices\"}"
)
public record CreateCourseResource(
    @Schema(
        description = "Course title",
        example = "Introduction to Java",
        minLength = 1,
        maxLength = 255
    )
    String title,

    @Schema(
        description = "Course description",
        example = "Learn Java fundamentals and best practices",
        minLength = 1,
        maxLength = 2000
    )
    String description
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if the title or description is null or blank.
     */
    public CreateCourseResource {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
    }
}
