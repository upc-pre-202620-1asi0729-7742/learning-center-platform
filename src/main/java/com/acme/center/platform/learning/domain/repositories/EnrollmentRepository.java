package com.acme.center.platform.learning.domain.repositories;

import com.acme.center.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

import java.util.List;
import java.util.Optional;

/**
 * Learning enrollment repository port.
 */
public interface EnrollmentRepository {
    Optional<Enrollment> findById(Long id);

    List<Enrollment> findAll();

    List<Enrollment> findAllByAcmeStudentRecordId(AcmeStudentRecordId studentRecordId);

    List<Enrollment> findAllByCourseId(Long courseId);

    Optional<Enrollment> findByAcmeStudentRecordIdAndCourseId(AcmeStudentRecordId studentRecordId, Long courseId);

    Enrollment save(Enrollment enrollment);
}

