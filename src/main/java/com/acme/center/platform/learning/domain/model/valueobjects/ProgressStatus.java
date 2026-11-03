package com.acme.center.platform.learning.domain.model.valueobjects;

/**
 * Enumeration representing the progress status of a tutorial.
 *
 * <p>
 * This enumeration is used to track the completion status of a specific tutorial for a student enrollment.
 * </p>
 */
public enum ProgressStatus {
    /**
     * The tutorial has not yet been started.
     */
    NOT_STARTED,
    /**
     * The tutorial is currently in progress.
     */
    STARTED,
    /**
     * The tutorial has been completed.
     */
    COMPLETED
}
