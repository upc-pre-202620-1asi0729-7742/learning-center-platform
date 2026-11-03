package com.acme.center.platform.profiles.domain.repositories;

import com.acme.center.platform.profiles.domain.model.aggregates.Profile;
import com.acme.center.platform.profiles.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;

/**
 * Profile repository port.
 */
public interface ProfileRepository {
    Optional<Profile> findById(Long id);

    Optional<Profile> findByEmailAddress(EmailAddress emailAddress);

    List<Profile> findAll();

    Profile save(Profile profile);

    boolean existsByEmailAddress(EmailAddress emailAddress);
}

