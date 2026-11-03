package com.acme.center.platform.profiles.application.acl;

import com.acme.center.platform.profiles.application.commandservices.ProfileCommandService;
import com.acme.center.platform.profiles.application.queryservices.ProfileQueryService;
import com.acme.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.acme.center.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.acme.center.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.acme.center.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Profiles ACL facade.
 *
 * <p>Provides a simplified integration surface for other bounded contexts that need profile
 * operations without coupling to Profiles internal models.</p>
 */
@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacadeImpl(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    /**
     * Creates a profile through the Profiles application command service.
     *
     * @return created profile identifier, or {@code 0L} when creation fails
     */
    public Long createProfile(
            String firstName,
            String lastName,
            String email,
            String street,
            String number,
            String city,
            String postalCode,
            String country) {
        var createProfileCommand = new CreateProfileCommand(
                firstName,
                lastName,
                email,
                street,
                number,
                city,
                postalCode,
                country);
        var result = profileCommandService.handle(createProfileCommand);
        return result.toOptional()
                .map(profile -> profile.getId())
                .orElse(0L);
    }

    /**
     * Fetches a profile identifier by email through the Profiles query service.
     *
     * @param email profile email address
     * @return profile identifier, or {@code 0L} when no profile is found
     */
    public Long fetchProfileIdByEmail(String email) {
        var getProfileByEmailQuery = new GetProfileByEmailQuery(new EmailAddress(email));
        var profile = profileQueryService.handle(getProfileByEmailQuery);
        return profile.isEmpty() ? Long.valueOf(0L) : profile.get().getId();
    }


}
