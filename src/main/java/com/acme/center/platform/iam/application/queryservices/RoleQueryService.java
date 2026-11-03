package com.acme.center.platform.iam.application.queryservices;

import com.acme.center.platform.iam.domain.model.entities.Role;
import com.acme.center.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.acme.center.platform.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for IAM role read queries.
 */
public interface RoleQueryService {
    /**
     * Handles retrieval of all roles.
     *
     * @param query query marker
     * @return list of roles
     */
    List<Role> handle(GetAllRolesQuery query);

    /**
     * Handles retrieval of a role by name.
     *
     * @param query role-name query
     * @return matching role, if found
     */
    Optional<Role> handle(GetRoleByNameQuery query);
}

