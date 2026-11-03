package com.acme.center.platform.iam.domain.repositories;

import com.acme.center.platform.iam.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

/**
 * IAM user repository port.
 */
public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);
}

