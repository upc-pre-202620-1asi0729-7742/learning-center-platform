package com.acme.center.platform.iam.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for IAM user persistence entities.
 */
@Repository
public interface UserPersistenceRepository extends JpaRepository<UserPersistenceEntity, Long>
{
    /**
     * This method is responsible for finding the user by username.
     * @param username The username.
     * @return The user object.
     */
    Optional<UserPersistenceEntity> findByUsername(String username);

    /**
     * This method is responsible for checking if the user exists by username.
     * @param username The username.
     * @return True if the user exists, false otherwise.
     */
    boolean existsByUsername(String username);

}

