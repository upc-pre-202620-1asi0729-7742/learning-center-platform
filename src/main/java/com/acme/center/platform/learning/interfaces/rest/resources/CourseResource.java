package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Course resource.
 */
@Schema(
    name = "CourseResponse",
    description = "Course information response",
    example = "{\"id\": 1, \"title\": \"Introduction to Java\", \"description\": \"Learn Java fundamentals and best practices\"}"
)
public record CourseResource(
    @Schema(description = "Course unique identifier", example = "1")
    Long id,

    @Schema(description = "Course title", example = "Introduction to Java")
    String title,

    @Schema(description = "Course description", example = "Learn Java fundamentals and best practices")
    String description
) {
}
