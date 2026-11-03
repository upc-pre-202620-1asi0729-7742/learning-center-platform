package com.acme.center.platform.learning.domain.repositories;

import com.acme.center.platform.learning.domain.model.aggregates.Student;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.model.valueobjects.ProfileId;

import java.util.Optional;

/**
 * Learning student repository port.
 */
public interface StudentRepository {
    Optional<Student> findById(Long id);

    Optional<Student> findByAcmeStudentRecordId(AcmeStudentRecordId studentRecordId);

    Optional<Student> findByProfileId(ProfileId profileId);

    Student save(Student student);

    boolean existsByAcmeStudentRecordId(AcmeStudentRecordId studentRecordId);
}

