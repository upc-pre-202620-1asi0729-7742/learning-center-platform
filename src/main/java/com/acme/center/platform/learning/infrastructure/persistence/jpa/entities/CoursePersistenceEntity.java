package com.acme.center.platform.learning.infrastructure.persistence.jpa.entities;

import com.acme.center.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for courses.
 */
@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class CoursePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningPathItemPersistenceEntity> learningPathItems = new ArrayList<>();
}

