package com.acme.center.platform.learning.application.internal.commandservices;

import com.acme.center.platform.learning.application.commandservices.StudentCommandService;
import com.acme.center.platform.learning.application.internal.outboundservices.acl.ExternalProfileService;
import com.acme.center.platform.learning.domain.model.aggregates.Student;
import com.acme.center.platform.learning.domain.model.commands.CreateStudentByProfileIdCommand;
import com.acme.center.platform.learning.domain.model.commands.CreateStudentCommand;
import com.acme.center.platform.learning.domain.model.commands.UpdateStudentMetricsOnTutorialCompletedCommand;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.model.valueobjects.ProfileId;
import com.acme.center.platform.learning.domain.repositories.StudentRepository;
import com.acme.center.platform.shared.application.result.ApplicationError;
import com.acme.center.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes student commands.
 */
@Service
public class StudentCommandServiceImpl implements StudentCommandService {
    private final StudentRepository studentRepository;
    private final ExternalProfileService externalProfileService;

    public StudentCommandServiceImpl(StudentRepository studentRepository, ExternalProfileService externalProfileService) {
        this.studentRepository = studentRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public Result<AcmeStudentRecordId, ApplicationError> handle(CreateStudentCommand command) {
        var profileId = externalProfileService.fetchProfileByEmail(command.email());

        if (profileId.isEmpty()) {
            return externalProfileService.createProfile(
                            command.firstName(), command.lastName(), command.email(),
                            command.street(), command.number(), command.city(),
                            command.postalCode(), command.country())
                    .map(id -> studentRepository.findByProfileId(id)
                            .map(student -> Result.<AcmeStudentRecordId, ApplicationError>success(student.getAcmeStudentRecordId()))
                            .orElseGet(() -> Result.failure(
                                    ApplicationError.unexpected("create-student", "Student record not found after profile creation")
                            )))
                    .orElseGet(() -> Result.failure(ApplicationError.unexpected("create-student", "Unable to create student profile")));
        }

        var existingStudent = studentRepository.findByProfileId(profileId.get());
        if (existingStudent.isPresent()) {
            var existingId = existingStudent.get().getAcmeStudentRecordId().studentRecordId();
            return Result.failure(ApplicationError.conflict("Student", "Student with id '%s' already exists".formatted(existingId)));
        }

        try {
            var student = new Student(profileId.get());
            student = studentRepository.save(student);
            return Result.success(student.getAcmeStudentRecordId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-student", e.getMessage()));
        }
    }

    @Override
    public Result<AcmeStudentRecordId, ApplicationError> handle(CreateStudentByProfileIdCommand command) {
        var studentRecordId = studentRepository.findByProfileId(new ProfileId(command.profileId()))
                .orElseGet(() -> {
                    var student = new Student(command.profileId());
                    return studentRepository.save(student);
                })
                .getAcmeStudentRecordId();
        return Result.success(studentRecordId);
    }

    @Override
    public Result<AcmeStudentRecordId, ApplicationError> handle(UpdateStudentMetricsOnTutorialCompletedCommand command) {
        return studentRepository.findByAcmeStudentRecordId(command.studentRecordId()).map(student -> {
            student.updateMetricsOnTutorialCompleted();
            var updatedStudent = studentRepository.save(student);
            return Result.<AcmeStudentRecordId, ApplicationError>success(updatedStudent.getAcmeStudentRecordId());
        }).orElseGet(() -> Result.failure(
                ApplicationError.notFound("Student", command.studentRecordId().studentRecordId())
        ));
    }
}
