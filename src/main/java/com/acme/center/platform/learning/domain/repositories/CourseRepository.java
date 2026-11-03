package com.acme.center.platform.learning.domain.repositories;

import com.acme.center.platform.learning.domain.model.aggregates.Course;

import java.util.List;
import java.util.Optional;

/**
 * Learning course repository port.
 */
public interface CourseRepository {
    Optional<Course> findById(Long id);

    List<Course> findAll();

    Optional<Course> findByTitle(String title);

    Course save(Course course);

    boolean existsById(Long id);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdIsNot(String title, Long id);

    void deleteById(Long id);
}

