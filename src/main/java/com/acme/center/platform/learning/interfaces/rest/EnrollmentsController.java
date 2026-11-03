package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.application.commandservices.EnrollmentCommandService;
import com.acme.center.platform.learning.application.queryservices.EnrollmentQueryService;
import com.acme.center.platform.learning.domain.model.commands.CancelEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.commands.ConfirmEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.commands.RejectEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import com.acme.center.platform.learning.domain.model.queries.GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery;
import com.acme.center.platform.learning.interfaces.rest.resources.EnrollmentResource;
import com.acme.center.platform.learning.interfaces.rest.resources.RequestEnrollmentResource;
import com.acme.center.platform.learning.interfaces.rest.transform.EnrollmentResourceFromEntityAssembler;
import com.acme.center.platform.learning.interfaces.rest.transform.RequestEnrollmentCommandFromResourceAssembler;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;
import com.acme.center.platform.shared.interfaces.rest.resources.MessageResource;
import com.acme.center.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes enrollment resources and enrollment lifecycle endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/enrollments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Enrollments", description = "Enrollment management endpoints")
public class EnrollmentsController {
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    /**
     * Constructor
     *
     * @param enrollmentCommandService Enrollment Command Service
     * @param enrollmentQueryService   Enrollment Query Service
     */
    public EnrollmentsController(EnrollmentCommandService enrollmentCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    /**
     * Request Enrollment
     *
     * @param resource The {@link RequestEnrollmentResource} object containing the request data
     * @return The {@link EnrollmentResource} Resource of the requested enrollment
     */
    @PostMapping
    @Operation(
        summary = "Request enrollment",
        description = "Creates a new enrollment request for a student to enroll in a course. Enrollment requires approval."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Enrollment requested successfully",
                content = @Content(schema = @Schema(implementation = EnrollmentResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid enrollment data"),
            @ApiResponse(responseCode = "409", description = "Conflict - Student already enrolled in course")
    })
    public ResponseEntity<?> requestEnrollment(@RequestBody RequestEnrollmentResource resource) {
        var requestEnrollmentCommand = RequestEnrollmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = enrollmentCommandService.handle(requestEnrollmentCommand)
                .flatMap(enrollmentId -> enrollmentQueryService.handle(new GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery(
                                requestEnrollmentCommand.studentRecordId(), requestEnrollmentCommand.courseId()))
                        .<Result<com.acme.center.platform.learning.domain.model.aggregates.Enrollment, ApplicationError>>
                                map(Result::success)
                        .orElseGet(() -> Result.failure(
                                ApplicationError.notFound("Enrollment", enrollmentId.toString())
                        )));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EnrollmentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Confirm Enrollment
     *
     * @param enrollmentId The enrollment ID
     * @return The {@link MessageResource} Resource of the confirmation
     */
    @PostMapping("/{enrollmentId}/confirmations")
    @Operation(
        summary = "Confirm enrollment",
        description = "Approves and confirms a pending enrollment request. Student becomes active in the course."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Enrollment confirmed successfully",
                content = @Content(schema = @Schema(implementation = MessageResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Enrollment not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Enrollment already confirmed or invalid state")
    })
    public ResponseEntity<?> confirmEnrollment(
            @PathVariable
            @Parameter(description = "Enrollment identifier", example = "1", required = true)
            Long enrollmentId
    ) {
        var confirmEnrollmentCommand = new ConfirmEnrollmentCommand(enrollmentId);
        var result = enrollmentCommandService.handle(confirmEnrollmentCommand)
                .map(_ -> new MessageResource("Enrollment confirmed successfully"));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.OK);
    }

    /**
     * Reject Enrollment
     *
     * @param enrollmentId The enrollment ID.
     * @return MessageResource with the enrollment confirmation message.
     */
    @PostMapping("/{enrollmentId}/rejections")
    @Operation(
        summary = "Reject enrollment",
        description = "Rejects a pending enrollment request. Student cannot access the course."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Enrollment rejected successfully",
                content = @Content(schema = @Schema(implementation = MessageResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Enrollment not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Enrollment not in valid state for rejection")
    })
    public ResponseEntity<?> rejectEnrollment(
            @PathVariable
            @Parameter(description = "Enrollment identifier", example = "1", required = true)
            Long enrollmentId
    ) {
        var rejectEnrollmentCommand = new RejectEnrollmentCommand(enrollmentId);
        var result = enrollmentCommandService.handle(rejectEnrollmentCommand)
                .map(_ -> new MessageResource("Enrollment rejected successfully"));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.OK);
    }

    /**
     * Cancel Enrollment
     *
     * @param enrollmentId The enrollment ID.
     * @return MessageResource with the cancellation confirmation message.
     */
    @PostMapping("/{enrollmentId}/cancellations")
    @Operation(
        summary = "Cancel enrollment",
        description = "Cancels an active or pending enrollment. Student loses access to the course."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Enrollment cancelled successfully",
                content = @Content(schema = @Schema(implementation = MessageResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Enrollment not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Enrollment not in valid state for cancellation")
    })
    public ResponseEntity<?> cancelEnrollment(
            @PathVariable
            @Parameter(description = "Enrollment identifier", example = "1", required = true)
            Long enrollmentId
    ) {
        var cancelEnrollmentCommand = new CancelEnrollmentCommand(enrollmentId);
        var result = enrollmentCommandService.handle(cancelEnrollmentCommand)
                .map(_ -> new MessageResource("Enrollment cancelled successfully"));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.OK);
    }

    /**
     * Gets all the enrollments.
     *
     * @return The list of all the enrollment resources available.
     */
    @GetMapping
    @Operation(
        summary = "Get all enrollments",
        description = "Retrieves a list of all enrollments in the system with various statuses."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Enrollments retrieved successfully",
                content = @Content(schema = @Schema(implementation = EnrollmentResource.class))
            )
    })
    public ResponseEntity<List<EnrollmentResource>> getAllEnrollments() {
        var getAllEnrollmentsQuery = new GetAllEnrollmentsQuery();
        var enrollments = enrollmentQueryService.handle(getAllEnrollmentsQuery);
        var enrollmentResources = enrollments.stream().map(EnrollmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(enrollmentResources);
    }
}
