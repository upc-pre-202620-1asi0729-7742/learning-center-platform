package com.acme.center.platform.iam.interfaces.rest.transform;

import com.acme.center.platform.iam.domain.model.commands.SignInCommand;
import com.acme.center.platform.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler that translates {@link SignInResource} into {@link SignInCommand}.
 */
public class SignInCommandFromResourceAssembler {
    /**
     * Converts the incoming sign-in resource to an application command.
     *
     * @param signInResource sign-in payload from REST API
     * @return sign-in command consumed by the application layer
     */
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
