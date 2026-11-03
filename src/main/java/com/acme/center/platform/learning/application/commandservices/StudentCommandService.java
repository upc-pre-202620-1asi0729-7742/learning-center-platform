package com.acme.center.platform.learning.application.commandservices;

import com.acme.center.platform.learning.domain.model.commands.CreateStudentByProfileIdCommand;
import com.acme.center.platform.learning.domain.model.commands.CreateStudentCommand;
import com.acme.center.platform.learning.domain.model.commands.UpdateStudentMetricsOnTutorialCompletedCommand;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;

/**
 * Application service contract for commands over student records.
 */
public interface StudentCommandService {
    /**
     * Handles student creation from profile input data.
     *
     * @param command command containing student and profile fields
     * @return created student record id or an application error
     */
    Result<AcmeStudentRecordId, ApplicationError> handle(CreateStudentCommand command);

    /**
     * Handles student creation from an existing profile identifier.
     *
     * <p>Typically invoked by an integration/event flow after profile creation.</p>
     *
     * @param command command containing the profile id
     * @return created student record id or an application error
     */
    Result<AcmeStudentRecordId, ApplicationError> handle(CreateStudentByProfileIdCommand command);

    /**
     * Handles student metric updates after tutorial completion.
     *
     * @param command command containing the target student record id
     * @return updated student record id or an application error
     */
    Result<AcmeStudentRecordId, ApplicationError> handle(UpdateStudentMetricsOnTutorialCompletedCommand command);
}

