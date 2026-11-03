package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.application.commandservices.CourseCommandService;
import com.acme.center.platform.learning.application.queryservices.CourseQueryService;
import com.acme.center.platform.learning.domain.model.commands.DeleteCourseCommand;
import com.acme.center.platform.learning.domain.model.queries.GetAllCoursesQuery;
import com.acme.center.platform.learning.domain.model.queries.GetCourseByIdQuery;
import com.acme.center.platform.learning.interfaces.rest.resources.CourseResource;
import com.acme.center.platform.learning.interfaces.rest.resources.CreateCourseResource;
import com.acme.center.platform.learning.interfaces.rest.resources.UpdateCourseResource;
import com.acme.center.platform.learning.interfaces.rest.transform.CourseResourceFromEntityAssembler;
import com.acme.center.platform.learning.interfaces.rest.transform.CreateCourseCommandFromResourceAssembler;
import com.acme.center.platform.learning.interfaces.rest.transform.UpdateCourseCommandFromResourceAssembler;
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
 * REST controller that exposes course resources and course administration endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/courses", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses", description = "Course management endpoints")
public class CoursesController {
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;


    /**
     * Constructor
     *
     * @param courseCommandService The {@link CourseCommandService} instance
     * @param courseQueryService   The {@link CourseQueryService} instance
     */
    public CoursesController(CourseCommandService courseCommandService, CourseQueryService courseQueryService) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
    }

    /**
     * Create a new course
     *
     * @param resource The {@link CreateCourseResource} instance
     * @return The {@link CourseResource} resource for the created course
     */
    @PostMapping
    @Operation(summary = "Create a new course", description = "Creates a new course with title and description. Requires instructor or admin role.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Course created successfully",
                content = @Content(schema = @Schema(implementation = CourseResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseResource resource) {
        var createCourseCommand = CreateCourseCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = courseCommandService.handle(createCourseCommand)
                .flatMap(courseId -> courseQueryService.handle(new GetCourseByIdQuery(courseId))
                        .<Result<com.acme.center.platform.learning.domain.model.aggregates.Course, ApplicationError>>
                                map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Course", courseId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CourseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get course by id
     *
     * @param courseId The course id
     * @return The {@link CourseResource} resource for the course
     */
    @GetMapping("/{courseId}")
    @Operation(summary = "Get course by ID", description = "Retrieves a specific course by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Course found",
                content = @Content(schema = @Schema(implementation = CourseResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResource> getCourseById(
            @PathVariable
            @Parameter(description = "Unique course identifier", example = "1", required = true)
            Long courseId
    ) {
        var getCourseByIdQuery = new GetCourseByIdQuery(courseId);
        var course = courseQueryService.handle(getCourseByIdQuery);
        if (course.isEmpty()) return ResponseEntity.notFound().build();
        var courseEntity = course.get();
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(courseEntity);
        return ResponseEntity.ok(courseResource);
    }

    /**
     * Get all courses
     *
     * @return The list of {@link CourseResource} resources for all courses
     */
    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieves a list of all available courses.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Courses retrieved successfully",
                content = @Content(schema = @Schema(implementation = CourseResource.class))
            )
    })
    public ResponseEntity<List<CourseResource>> getAllCourses() {
        var courses = courseQueryService.handle(new GetAllCoursesQuery());
        var courseResources = courses.stream()
                .map(CourseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(courseResources);
    }

    /**
     * Update course
     *
     * @param courseId The course id
     * @param resource The {@link UpdateCourseResource} instance
     * @return The {@link CourseResource} resource for the updated course
     */
    @PutMapping("/{courseId}")
    @Operation(summary = "Update course", description = "Updates an existing course's title and description. Requires instructor or admin role.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Course updated successfully",
                content = @Content(schema = @Schema(implementation = CourseResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<?> updateCourse(
            @PathVariable
            @Parameter(description = "Unique course identifier", example = "1", required = true)
            Long courseId,
            @RequestBody UpdateCourseResource resource
    ) {
        var updateCourseCommand = UpdateCourseCommandFromResourceAssembler.toCommandFromResource(courseId, resource);
        var result = courseCommandService.handle(updateCourseCommand);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CourseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Delete course
     *
     * @param courseId The course id
     * @return The message for the deleted course
     */
    @DeleteMapping("/{courseId}")
    @Operation(summary = "Delete course", description = "Deletes a course. Requires instructor or admin role.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "Course deleted successfully"
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<?> deleteCourse(
            @PathVariable
            @Parameter(description = "Unique course identifier", example = "1", required = true)
            Long courseId
    ) {
        var deleteCourseCommand = new DeleteCourseCommand(courseId);
        var result = courseCommandService.handle(deleteCourseCommand)
                .map(_ -> new MessageResource("Course with given id successfully deleted"));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                message -> message,
                HttpStatus.OK
        );
    }
}