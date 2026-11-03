package com.acme.center.platform.learning.infrastructure.persistence.jpa.converters;

import com.acme.center.platform.learning.domain.model.valueobjects.ProfileId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts profile IDs between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class ProfileIdPersistenceConverter implements AttributeConverter<ProfileId, Long> {

    @Override
    public Long convertToDatabaseColumn(ProfileId attribute) {
        return attribute == null ? null : attribute.profileId();
    }

    @Override
    public ProfileId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new ProfileId(dbData);
    }
}

