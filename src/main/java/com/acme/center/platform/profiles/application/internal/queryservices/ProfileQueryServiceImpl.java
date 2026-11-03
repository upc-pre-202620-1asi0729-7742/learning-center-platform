package com.acme.center.platform.profiles.application.internal.queryservices;

import com.acme.center.platform.profiles.application.queryservices.ProfileQueryService;
import com.acme.center.platform.profiles.domain.model.aggregates.Profile;
import com.acme.center.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.acme.center.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.acme.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.acme.center.platform.profiles.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves Profiles bounded-context read queries.
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    /**
     * Creates the query service with the profile repository dependency.
     *
     * @param profileRepository profile repository port
     */
    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // inherited javadoc
    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    // inherited javadoc
    @Override
    public Optional<Profile> handle(GetProfileByEmailQuery query) {
        return profileRepository.findByEmailAddress(query.emailAddress());
    }

    // inherited javadoc
    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }
}
