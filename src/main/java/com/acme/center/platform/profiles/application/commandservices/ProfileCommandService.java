package com.acme.center.platform.profiles.application.commandservices;

import com.acme.center.platform.profiles.domain.model.aggregates.Profile;
import com.acme.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;

/**
 * Profile Command Service
 */
public interface ProfileCommandService {
    /**
     * Handle Create Profile Command
     *
     * @param command The {@link CreateProfileCommand} Command
     * @return A {@link Result} containing the created {@link Profile} on success,
     *         or an {@link ApplicationError} on failure (validation or business rule violation)
     */
    Result<Profile, ApplicationError> handle(CreateProfileCommand command);
}

