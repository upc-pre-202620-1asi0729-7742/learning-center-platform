package com.acme.center.platform.learning.domain.model.commands;

/**
 * Command to create a {@code Student} record for a profile that already exists.
 *
 * <p>This command is used when a {@code Student} needs to be created reactively
 * from a domain event (e.g. {@code ProfileCreatedEvent}) rather than through a
 * direct user-initiated request. The profile identity is already known at this
 * point, so no ACL lookup is required.</p>
 *
 * @param profileId The identity of the existing profile to associate with the new student.
 */
public record CreateStudentByProfileIdCommand(Long profileId) {
}

