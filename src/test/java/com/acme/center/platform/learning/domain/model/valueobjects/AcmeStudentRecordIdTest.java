package com.acme.center.platform.learning.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AcmeStudentRecordIdTest {

    @Test
    void defaultConstructorGeneratesUuidValue() {
        var recordId = new AcmeStudentRecordId();

        assertDoesNotThrow(() -> UUID.fromString(recordId.studentRecordId()));
    }

    @Test
    void canonicalConstructorAcceptsValidUuid() {
        var uuid = UUID.randomUUID().toString();

        var recordId = new AcmeStudentRecordId(uuid);

        assertEquals(uuid, recordId.studentRecordId());
    }

    @Test
    void canonicalConstructorRejectsBlankValue() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new AcmeStudentRecordId(" "));

        assertEquals("Student record id cannot be null or empty", exception.getMessage());
    }

    @Test
    void canonicalConstructorRejectsInvalidUuidValue() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new AcmeStudentRecordId("STU-2025-001"));

        assertEquals("Student record id must be a valid UUID", exception.getMessage());
    }
}

