package com.acme.center.platform.iam.application.queryservices;

import com.acme.center.platform.iam.domain.model.aggregates.User;
import com.acme.center.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.center.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.center.platform.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for IAM user read queries.
 */
public interface UserQueryService {
    /**
     * Handles retrieval of all users.
     *
     * @param query query marker
     * @return list of users
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Handles retrieval of a user by id.
     *
     * @param query user-id query
     * @return matching user, if found
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handles retrieval of a user by username.
     *
     * @param query username query
     * @return matching user, if found
     */
    Optional<User> handle(GetUserByUsernameQuery query);

}

