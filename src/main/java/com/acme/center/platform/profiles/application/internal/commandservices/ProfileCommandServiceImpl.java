package com.acme.center.platform.profiles.application.internal.commandservices;

import com.acme.center.platform.profiles.application.commandservices.ProfileCommandService;
import com.acme.center.platform.profiles.domain.model.aggregates.Profile;
import com.acme.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.acme.center.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.acme.center.platform.profiles.domain.repositories.ProfileRepository;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Profile Command Service Implementation
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    /**
     * Constructor
     *
     * @param profileRepository The {@link ProfileRepository} instance
     */
    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // inherited javadoc
    @Override
    public Result<Profile, ApplicationError> handle(CreateProfileCommand command) {
        try {
            var emailAddress = new EmailAddress(command.email());
            if (profileRepository.existsByEmailAddress(emailAddress)) {
                return Result.failure(ApplicationError.conflict(
                        "Profile",
                        "A profile with email address '%s' already exists".formatted(command.email())));
            }

            var profile = new Profile(command);
            var savedProfile = profileRepository.save(profile);
            return Result.success(savedProfile);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Profile", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "Profile creation",
                    e.getMessage()));
        }
    }
}

