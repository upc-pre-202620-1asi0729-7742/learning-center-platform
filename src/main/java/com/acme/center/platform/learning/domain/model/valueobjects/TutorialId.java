package com.acme.center.platform.learning.domain.model.valueobjects;


/**
 * Value object representing the unique identifier of a tutorial.
 *
 * <p>
 * This value object is used to uniquely identify a tutorial within the learning platform.
 * The tutorialId must be a non-negative Long value.
 * </p>
 *
 * @param tutorialId The unique identifier of the tutorial. It cannot be null or less than 0.
 */
public record TutorialId(Long tutorialId) {
    /**
     * Compact constructor for TutorialId.
     * Validates that the tutorialId is not null and is non-negative.
     * @throws IllegalArgumentException if the tutorialId is null or less than 0.
     */
    public TutorialId {
        if (tutorialId == null || tutorialId < 0) {
            throw new IllegalArgumentException("TutorialId cannot be null or less than 0");
        }
    }
}
