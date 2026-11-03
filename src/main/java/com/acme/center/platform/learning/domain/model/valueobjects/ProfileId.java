package com.acme.center.platform.learning.domain.model.valueobjects;

/**
 * Value object representing the profile id.
 *
 * <p>
 * This value object is used to link a student in the learning domain to a profile in the profiles domain.
 * It must be a positive Long value.
 * </p>
 *
 * @param profileId The profile id. It cannot be null or less than 1.
 */
public record ProfileId(Long profileId) {
    /**
     * Compact constructor for ProfileId.
     * Validates that the profileId is not null and is greater than or equal to 1.
     * @throws IllegalArgumentException if the profileId is null or less than 1.
     */
    public ProfileId {
        if (profileId == null || profileId < 1) {
            throw new IllegalArgumentException("Profile id cannot be null or less than 1");
        }
    }
}
