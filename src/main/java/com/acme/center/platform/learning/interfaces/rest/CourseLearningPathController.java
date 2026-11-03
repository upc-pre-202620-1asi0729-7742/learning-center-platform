package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.application.commandservices.CourseCommandService;
import com.acme.center.platform.learning.application.queryservices.CourseQueryService;
import com.acme.center.platform.learning.domain.model.commands.AddTutorialToCourseLearningPathCommand;
import com.acme.center.platform.learning.domain.model.queries.GetLearningPathItemByCourseIdAndTutorialIdQuery;
import com.acme.center.platform.learning.domain.model.valueobjects.TutorialId;
import com.acme.center.platform.learning.interfaces.rest.resources.LearningPathItemResource;
import com.acme.center.platform.learning.interfaces.rest.transform.LearningPathItemResourceFromEntityAssembler;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing the learning path of a course.
 * Enables instructors to add tutorials/lessons to a course's learning path.
 */
@RestController
@RequestMapping(value = "/api/v1/courses/{courseId}/learning-path-items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses", description = "Course learning path management endpoints")
public class CourseLearningPathController {
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;

    /**
     * Constructor.
     *
     * @param courseCommandService the {@link CourseCommandService} service
     * @param courseQueryService   the {@link CourseQueryService} service
     */
    public CourseLearningPathController(CourseCommandService courseCommandService, CourseQueryService courseQueryService) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
    }

    /**
     * Add a tutorial to the learning path of a course.
     *
     * @param courseId   the course identifier
     * @param tutorialId the tutorial identifier
     * @return the learning path item resource
     */
    @PostMapping("/{tutorialId}")
    @Operation(
        summary = "Add tutorial to course learning path",
        description = "Adds a tutorial/lesson to a course's learning path sequence. Requires instructor or admin role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Tutorial added to learning path successfully",
                content = @Content(schema = @Schema(implementation = LearningPathItemResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid course or tutorial ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Course or tutorial not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Tutorial already in learning path")
    })
    public ResponseEntity<?> addTutorialToCourseLearningPath(
            @PathVariable
            @Parameter(description = "Course unique identifier", example = "1", required = true)
            Long courseId,

            @PathVariable
            @Parameter(description = "Tutorial unique identifier", example = "5", required = true)
            Long tutorialId
    ) {
        var command = new AddTutorialToCourseLearningPathCommand(new TutorialId(tutorialId), courseId);
        var result = courseCommandService.handle(command)
                .flatMap(savedCourseId -> courseQueryService.handle(new GetLearningPathItemByCourseIdAndTutorialIdQuery(
                                savedCourseId, new TutorialId(tutorialId)))
                        .<Result<com.acme.center.platform.learning.domain.model.entities.LearningPathItem, ApplicationError>>
                                map(Result::success)
                        .orElseGet(() -> Result.failure(
                                ApplicationError.notFound("LearningPathItem", tutorialId.toString())
                        )));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                LearningPathItemResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
