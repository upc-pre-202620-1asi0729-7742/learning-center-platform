package com.acme.center.platform.learning.interfaces.rest.transform;

import com.acme.center.platform.learning.domain.model.aggregates.Student;
import com.acme.center.platform.learning.interfaces.rest.resources.StudentResource;

/**
 * Assembler that converts {@link Student} aggregates into REST {@link StudentResource} objects.
 */
public class StudentResourceFromEntityAssembler {
    /**
     * Converts a student aggregate to its REST representation.
     *
     * @param entity student aggregate
     * @return student resource
     */
    public static StudentResource toResourceFromEntity(Student entity) {
        return new StudentResource(entity.getStudentRecordId(), entity.getProfileIdValue(), entity.getTotalCompletedCourses(), entity.getTotalCompletedTutorials());
    }
}
