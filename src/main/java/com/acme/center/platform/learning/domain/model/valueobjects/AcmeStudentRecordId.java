package com.acme.center.platform.learning.domain.model.valueobjects;

import java.util.UUID;

/**
 * Value object representing the student record id.
 *
 * <p>
 * This value object is used to represent the student record id.
 * The identifier is stored as a UUID string.
 * It throws an IllegalArgumentException if the student record id is null, empty, or not a valid UUID.
 * </p>
 *
 * @param studentRecordId The student record id. It must be a valid UUID string.
 */
public record AcmeStudentRecordId(String studentRecordId) {
    /**
     * Default constructor.
     * Generates a new random UUID and sets it as the student record id.
     */
    public AcmeStudentRecordId() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Compact constructor for AcmeStudentRecordId.
     * Validates that the studentRecordId is a valid UUID string.
     * @throws IllegalArgumentException if the studentRecordId is null, blank or not a valid UUID.
     */
    public AcmeStudentRecordId {
        if (studentRecordId == null || studentRecordId.isBlank()) {
            throw new IllegalArgumentException("Student record id cannot be null or empty");
        }
        try {
            UUID.fromString(studentRecordId);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Student record id must be a valid UUID", exception);
        }
    }
}
