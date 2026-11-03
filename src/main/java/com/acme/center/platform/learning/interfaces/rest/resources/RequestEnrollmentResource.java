package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request enrollment resource.
 */
@Schema(
    name = "EnrollmentRequest",
    description = "Request payload for enrolling a student in a course",
    example = "{\"studentRecordId\": \"123e4567-e89b-12d3-a456-426614174000\", \"courseId\": 1}"
)
public record RequestEnrollmentResource(
    @Schema(
        description = "Student record identifier as UUID",
        example = "123e4567-e89b-12d3-a456-426614174000",
        format = "uuid"
    )
    String studentRecordId,

    @Schema(
        description = "Course identifier to enroll in",
        example = "1"
    )
    Long courseId
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if the student record id or course id is null or invalid.
     */
    public RequestEnrollmentResource {
        if (studentRecordId == null || studentRecordId.isBlank()) {
            throw new IllegalArgumentException("Student record id is required");
        }
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course id is required");
        }
    }
}
