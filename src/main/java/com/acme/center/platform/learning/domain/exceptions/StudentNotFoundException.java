package com.acme.center.platform.learning.domain.exceptions;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

/**
 * Exception thrown when a student cannot be found by {@link AcmeStudentRecordId}.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(AcmeStudentRecordId studentRecordId) {
        super("Student with Acme student record id %s not found".formatted(studentRecordId.studentRecordId()));
    }
}
