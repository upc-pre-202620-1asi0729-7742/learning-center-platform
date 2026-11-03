package com.acme.center.platform.learning.application.internal.eventhandlers;

import com.acme.center.platform.learning.application.commandservices.StudentCommandService;
import com.acme.center.platform.learning.domain.model.commands.CreateStudentByProfileIdCommand;
import com.acme.center.platform.profiles.interfaces.events.ProfileCreatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer event handler for {@link ProfileCreatedIntegrationEvent}.
 *
 * <p>When a new profile is created in the {@code profiles} bounded context,
 * this handler automatically provisions a corresponding {@code Student} record
 * in the {@code learning} bounded context. This removes the need for
 * {@code StudentCommandServiceImpl} to directly orchestrate student creation
 * when a profile is first created.</p>
 *
 * <p>Note: this handler references {@code profiles.interfaces.events} — the
 * published language of the {@code profiles} context — never an internal
 * domain type from {@code profiles.domain}.</p>
 *
 * <p>Because Spring's {@link EventListener} dispatches events synchronously by
 * default, the student record is fully persisted before control returns to the
 * caller that triggered the profile creation.</p>
 */
@Service("learningProfileCreatedEventHandler")
@Slf4j
public class ProfileCreatedEventHandler {
    private final StudentCommandService studentCommandService;

    /**
     * Constructor.
     *
     * @param studentCommandService the student command service
     * @see StudentCommandService
     */
    public ProfileCreatedEventHandler(StudentCommandService studentCommandService) {
        this.studentCommandService = studentCommandService;
    }

    /**
     * Handles the {@link ProfileCreatedIntegrationEvent}.
     *
     * <p>Creates a {@code Student} record associated with the newly created profile.
     * Delegates to {@link StudentCommandService#handle(CreateStudentByProfileIdCommand)}
     * which is idempotent — if a student for this profile already exists, it
     * returns the existing record without creating a duplicate.</p>
     *
     * @param event the {@link ProfileCreatedIntegrationEvent} published by the {@code profiles} context
     */
    @EventListener
    public void on(ProfileCreatedIntegrationEvent event) {
        var command = new CreateStudentByProfileIdCommand(event.profileId());
        var result = studentCommandService.handle(command);

        if (result instanceof com.acme.center.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to create student for profile {}: {}", event.profileId(), error.message());
        }
    }
}

