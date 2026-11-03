package com.acme.center.platform.learning.application.commandservices;

import com.acme.center.platform.learning.domain.model.commands.*;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;

/**
 * Application service contract for enrollment lifecycle commands.
 */
public interface EnrollmentCommandService {
    /**
     * Handles enrollment request creation.
     *
     * @param command enrollment request command
     * @return requested enrollment identifier or an application error
     * @see RequestEnrollmentCommand
     */
    Result<Long, ApplicationError> handle(RequestEnrollmentCommand command);

    /**
     * Handles enrollment confirmation.
     *
     * @param command enrollment confirmation command
     * @return confirmed enrollment identifier or an application error
     * @see ConfirmEnrollmentCommand
     */
    Result<Long, ApplicationError> handle(ConfirmEnrollmentCommand command);

    /**
     * Handles enrollment rejection.
     *
     * @param command enrollment rejection command
     * @return rejected enrollment identifier or an application error
     * @see RejectEnrollmentCommand
     */
    Result<Long, ApplicationError> handle(RejectEnrollmentCommand command);

    /**
     * Handles enrollment cancellation.
     *
     * @param command enrollment cancellation command
     * @return canceled enrollment identifier or an application error
     * @see CancelEnrollmentCommand
     */
    Result<Long, ApplicationError> handle(CancelEnrollmentCommand command);

    /**
     * Handles tutorial completion for an enrollment.
     *
     * @param command tutorial completion command
     * @return enrollment identifier or an application error
     * @see CompleteTutorialForEnrollmentCommand
     */
    Result<Long, ApplicationError> handle(CompleteTutorialForEnrollmentCommand command);
}

