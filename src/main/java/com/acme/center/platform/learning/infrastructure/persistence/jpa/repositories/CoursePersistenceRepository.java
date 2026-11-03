package com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.learning.infrastructure.persistence.jpa.entities.CoursePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for course persistence entities.
 */
@Repository
public interface CoursePersistenceRepository extends JpaRepository<CoursePersistenceEntity, Long> {
    Optional<CoursePersistenceEntity> findByTitle(String title);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdIsNot(String title, Long id);
}

