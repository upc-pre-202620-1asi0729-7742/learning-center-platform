package com.acme.center.platform.learning.application.internal.commandservices;

import com.acme.center.platform.learning.application.commandservices.CourseCommandService;
import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.domain.model.commands.AddTutorialToCourseLearningPathCommand;
import com.acme.center.platform.learning.domain.model.commands.CreateCourseCommand;
import com.acme.center.platform.learning.domain.model.commands.DeleteCourseCommand;
import com.acme.center.platform.learning.domain.model.commands.UpdateCourseCommand;
import com.acme.center.platform.learning.domain.repositories.CourseRepository;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes course commands.
 */
@Service
public class CourseCommandServiceImpl implements CourseCommandService {
    private final CourseRepository courseRepository;

    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateCourseCommand command) {
        if (courseRepository.existsByTitle(command.title()))
            return Result.failure(ApplicationError.conflict("Course", "Title '%s' already exists".formatted(command.title())));
        var course = new Course(command);
        try {
            course = courseRepository.save(course);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-course", e.getMessage()));
        }
        return Result.success(course.getId());
    }

    @Override
    public Result<Course, ApplicationError> handle(UpdateCourseCommand command) {
        if (courseRepository.existsByTitleAndIdIsNot(command.title(), command.courseId()))
            return Result.failure(ApplicationError.conflict("Course", "Title '%s' already exists".formatted(command.title())));
        var result = courseRepository.findById(command.courseId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("Course", command.courseId().toString()));
        var courseToUpdate = result.get();
        try {
            var updatedCourse = courseRepository.save(courseToUpdate.updateInformation(command.title(), command.description()));
            return Result.success(updatedCourse);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-course", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(DeleteCourseCommand command) {
        if (!courseRepository.existsById(command.courseId())) {
            return Result.failure(ApplicationError.notFound("Course", command.courseId().toString()));
        }
        try {
            courseRepository.deleteById(command.courseId());
            return Result.success(command.courseId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("delete-course", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(AddTutorialToCourseLearningPathCommand command) {
        if (!courseRepository.existsById(command.courseId())) {
            return Result.failure(ApplicationError.notFound("Course", command.courseId().toString()));
        }
        try {
            courseRepository.findById(command.courseId()).map(course -> {
                course.addTutorialToLearningPath(command.tutorialId());
                courseRepository.save(course);
                return course;
            });
            return Result.success(command.courseId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("add-tutorial-to-course", e.getMessage()));
        }
    }
}
