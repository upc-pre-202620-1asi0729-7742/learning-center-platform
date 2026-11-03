package com.acme.center.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.acme.center.platform.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for profile persistence entities.
 */
@Repository
public interface ProfilePersistenceRepository extends JpaRepository<ProfilePersistenceEntity, Long> {

    @Query("select profile from ProfilePersistenceEntity profile where profile.emailAddress = :emailAddress")
    Optional<ProfilePersistenceEntity> findByEmailAddress(@Param("emailAddress") EmailAddress emailAddress);

    @Query("select count(profile) from ProfilePersistenceEntity profile where profile.emailAddress = :emailAddress")
    long countByEmailAddress(@Param("emailAddress") EmailAddress emailAddress);
}


