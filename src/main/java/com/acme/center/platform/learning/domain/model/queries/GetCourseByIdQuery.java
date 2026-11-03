package com.acme.center.platform.learning.domain.model.queries;

/**
 * Query to get course by id.
 *
 * @param courseId The ID of the course to retrieve. Cannot be null or less than or equal to 0.
 */
public record GetCourseByIdQuery(Long courseId) {
    /**
     * Compact constructor for GetCourseByIdQuery.
     * Validates that the courseId is not null and is greater than 0.
     * @throws IllegalArgumentException if courseId is null or less than or equal to 0.
     */
    public GetCourseByIdQuery {
        if (courseId == null || courseId <= 0) throw new IllegalArgumentException("Course id is required.");
    }
}
