package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.application.commandservices.StudentCommandService;
import com.acme.center.platform.learning.application.queryservices.StudentQueryService;
import com.acme.center.platform.learning.domain.model.queries.GetStudentByAcmeStudentRecordIdQuery;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.interfaces.rest.resources.CreateStudentResource;
import com.acme.center.platform.learning.interfaces.rest.resources.StudentResource;
import com.acme.center.platform.learning.interfaces.rest.transform.CreateStudentCommandFromResourceAssembler;
import com.acme.center.platform.learning.interfaces.rest.transform.StudentResourceFromEntityAssembler;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The {@link StudentsController} class defines the RESTful API endpoints for the students.
 */
@RestController
@RequestMapping(value = "/api/v1/students", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Student management endpoints")
public class StudentsController {
    private final StudentCommandService studentCommandService;
    private final StudentQueryService studentQueryService;

    /**
     * Instantiates a new {@link StudentsController} instance.
     *
     * @param studentCommandService The {@link StudentCommandService} instance
     * @param studentQueryService   The {@link StudentQueryService} instance
     */
    public StudentsController(StudentCommandService studentCommandService, StudentQueryService studentQueryService) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
    }

    /**
     * Create a new student
     *
     * @param resource The {@link CreateStudentResource} instance
     * @return The {@link StudentResource} resource for the created student, or a bad request response if the student was not created
     */
    @PostMapping
    @Operation(
        summary = "Create a new student",
        description = "Creates a new student record with complete profile information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Student created successfully",
                content = @Content(schema = @Schema(implementation = StudentResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Conflict - Student already exists")
    })
    public ResponseEntity<?> createStudent(@RequestBody CreateStudentResource resource) {
        var createStudentCommand = CreateStudentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = studentCommandService.handle(createStudentCommand)
                .flatMap(studentRecordId -> studentQueryService.handle(new GetStudentByAcmeStudentRecordIdQuery(studentRecordId))
                        .<Result<com.acme.center.platform.learning.domain.model.aggregates.Student, ApplicationError>>
                                map(Result::success)
                        .orElseGet(() -> Result.failure(
                                ApplicationError.notFound("Student", studentRecordId.studentRecordId())
                        )));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                StudentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get student by Acme Student Record ID
     *
     * @param studentRecordId The Acme Student Record ID
     * @return The {@link StudentResource} resource for the student, or a not found response if the student was not found
     */
    @GetMapping("/{studentRecordId}")
    @Operation(
        summary = "Get student by Record ID",
        description = "Retrieves a student's information by their unique student record identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Student retrieved successfully",
                content = @Content(schema = @Schema(implementation = StudentResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResource> getStudentByAcmeStudentRecordId(
            @PathVariable
            @Parameter(
                description = "Student record identifier as UUID",
                example = "123e4567-e89b-12d3-a456-426614174000",
                required = true
            )
            String studentRecordId
    ) {
        var acmeStudentRecordId = new AcmeStudentRecordId(studentRecordId);
        var getStudentByAcmeStudentRecordIdQuery = new GetStudentByAcmeStudentRecordIdQuery(acmeStudentRecordId);
        var student = studentQueryService.handle(getStudentByAcmeStudentRecordIdQuery);
        if (student.isEmpty()) return ResponseEntity.notFound().build();
        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return ResponseEntity.ok(studentResource);
    }


}
