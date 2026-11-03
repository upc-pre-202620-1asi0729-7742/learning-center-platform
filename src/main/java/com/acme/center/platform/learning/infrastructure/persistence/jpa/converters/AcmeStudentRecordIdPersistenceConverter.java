package com.acme.center.platform.learning.infrastructure.persistence.jpa.converters;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts ACME student record IDs between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class AcmeStudentRecordIdPersistenceConverter implements AttributeConverter<AcmeStudentRecordId, String> {

    @Override
    public String convertToDatabaseColumn(AcmeStudentRecordId attribute) {
        return attribute == null ? null : attribute.studentRecordId();
    }

    @Override
    public AcmeStudentRecordId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new AcmeStudentRecordId(dbData);
    }
}

