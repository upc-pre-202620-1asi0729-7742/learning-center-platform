package com.acme.center.platform.iam.interfaces.rest.transform;

import com.acme.center.platform.iam.domain.model.commands.SignUpCommand;
import com.acme.center.platform.iam.domain.model.entities.Role;
import com.acme.center.platform.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

/**
 * Assembler that translates {@link SignUpResource} into {@link SignUpCommand}.
 */
public class SignUpCommandFromResourceAssembler {
    /**
     * Converts the incoming sign-up resource to an application command.
     *
     * @param resource sign-up payload from REST API
     * @return sign-up command consumed by the application layer
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList() : new ArrayList<Role>();
        return new SignUpCommand(resource.username(), resource.password(), roles);
    }
}
