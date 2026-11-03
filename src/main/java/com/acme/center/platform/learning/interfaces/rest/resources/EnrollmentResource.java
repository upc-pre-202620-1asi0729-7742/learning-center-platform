package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enrollment resource.
 */
@Schema(
    name = "EnrollmentResponse",
    description = "Enrollment information response",
    example = "{\"enrollmentId\": 1, \"studentRecordId\": \"123e4567-e89b-12d3-a456-426614174000\", \"courseId\": 1, \"status\": \"PENDING\"}"
)
public record EnrollmentResource(
    @Schema(description = "Enrollment unique identifier", example = "1")
    Long enrollmentId,

    @Schema(description = "Student record identifier as UUID", example = "123e4567-e89b-12d3-a456-426614174000", format = "uuid")
    String studentRecordId,

    @Schema(description = "Course identifier", example = "1")
    Long courseId,

    @Schema(description = "Enrollment status", example = "confirmed", allowableValues = {"requested", "confirmed", "rejected", "cancelled"})
    String status,

    @Schema(description = "Total days elapsed across all progress record items", example = "5")
    long daysElapsed
) {
}
