package com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.model.valueobjects.ProfileId;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.entities.StudentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for student persistence entities.
 */
@Repository
public interface StudentPersistenceRepository extends JpaRepository<StudentPersistenceEntity, Long> {
    Optional<StudentPersistenceEntity> findByAcmeStudentRecordId(AcmeStudentRecordId studentRecordId);

    Optional<StudentPersistenceEntity> findByProfileId(ProfileId profileId);

    boolean existsByAcmeStudentRecordId(AcmeStudentRecordId studentRecordId);
}

