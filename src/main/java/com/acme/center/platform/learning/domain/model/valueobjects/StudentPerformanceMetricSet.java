package com.acme.center.platform.learning.domain.model.valueobjects;

/**
 * Value object representing the student performance metric set.
 *
 * <p>
 * This value object is used to track the performance metrics of a student,
 * specifically the total number of completed courses and tutorials.
 * It is immutable; any increment operation returns a new instance.
 * </p>
 *
 * @param totalCompletedCourses The total number of completed courses. It cannot be null or less than 0.
 * @param totalCompletedTutorials The total number of completed tutorials. It cannot be null or less than 0.
 */
public record StudentPerformanceMetricSet(Integer totalCompletedCourses, Integer totalCompletedTutorials) {
    /**
     * Default constructor.
     * Initializes the metrics with zeros.
     */
    public StudentPerformanceMetricSet() {
        this(0, 0);
    }

    /**
     * Compact constructor for StudentPerformanceMetricSet.
     * Validates that the metrics are not null and are non-negative.
     * @throws IllegalArgumentException if totalCompletedCourses or totalCompletedTutorials is null or less than 0.
     */
    public StudentPerformanceMetricSet {
        if (totalCompletedCourses == null || totalCompletedCourses < 0)
            throw new IllegalArgumentException("Total completed courses cannot be null or less than 0");
        if (totalCompletedTutorials == null || totalCompletedTutorials < 0)
            throw new IllegalArgumentException("Total completed tutorials cannot be null or less than 0");
    }

    /**
     * Increments the total number of completed courses by 1.
     * @return A new instance of StudentPerformanceMetricSet with the incremented course count.
     */
    public StudentPerformanceMetricSet incrementTotalCompletedCourses() {
        return new StudentPerformanceMetricSet(totalCompletedCourses + 1, totalCompletedTutorials);
    }

    /**
     * Increments the total number of completed tutorials by 1.
     * @return A new instance of StudentPerformanceMetricSet with the incremented tutorial count.
     */
    public StudentPerformanceMetricSet incrementTotalCompletedTutorials() {
        return new StudentPerformanceMetricSet(totalCompletedCourses, totalCompletedTutorials + 1);
    }
}
