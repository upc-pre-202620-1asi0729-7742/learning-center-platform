package com.acme.center.platform.learning.domain.model.events;

import com.acme.center.platform.learning.domain.model.valueobjects.TutorialId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Domain event representing the completion of a tutorial.
 *
 * <p>
 * This event is published when a student successfully completes a tutorial within an enrollment.
 * </p>
 */
@Getter
public class TutorialCompletedEvent extends ApplicationEvent {
    /**
     * The ID of the enrollment where the tutorial was completed.
     */
    private final Long enrollmentId;

    /**
     * The ID of the completed tutorial.
     */
    private final TutorialId tutorialId;

    /**
     * Constructor for TutorialCompletedEvent.
     * @param source The object that published the event.
     * @param enrollmentId The enrollment ID.
     * @param tutorialId The tutorial ID.
     */
    public TutorialCompletedEvent(Object source, Long enrollmentId, TutorialId tutorialId) {
        super(source);
        this.enrollmentId = enrollmentId;
        this.tutorialId = tutorialId;
    }
}
