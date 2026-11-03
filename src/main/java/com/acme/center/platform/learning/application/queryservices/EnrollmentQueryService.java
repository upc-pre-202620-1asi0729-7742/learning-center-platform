package com.acme.center.platform.learning.application.queryservices;

import com.acme.center.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.center.platform.learning.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for enrollment read queries.
 */
public interface EnrollmentQueryService {
    /**
     * Handles retrieval of enrollments by ACME student record id.
     *
     * @param query student-record-id query
     * @return list of enrollments for the given student
     * @see GetAllEnrollmentsByAcmeStudentRecordIdQuery
     */
    List<Enrollment> handle(GetAllEnrollmentsByAcmeStudentRecordIdQuery query);

    /**
     * Handles retrieval of an enrollment by id.
     *
     * @param query enrollment-id query
     * @return matching enrollment, if found
     * @see GetEnrollmentByIdQuery
     */
    Optional<Enrollment> handle(GetEnrollmentByIdQuery query);

    /**
     * Handles retrieval of all enrollments.
     *
     * @param query query marker
     * @return list of enrollments
     * @see GetAllEnrollmentsQuery
     */
    List<Enrollment> handle(GetAllEnrollmentsQuery query);

    /**
     * Handles retrieval of enrollments by course id.
     *
     * @param query course-id query
     * @return list of enrollments for the course
     * @see GetAllEnrollmentsByCourseIdQuery
     */
    List<Enrollment> handle(GetAllEnrollmentsByCourseIdQuery query);

    /**
     * Handles retrieval of an enrollment by student record id and course id.
     *
     * @param query student-and-course query
     * @return matching enrollment, if found
     * @see GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery
     */
    Optional<Enrollment> handle(GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery query);
}

