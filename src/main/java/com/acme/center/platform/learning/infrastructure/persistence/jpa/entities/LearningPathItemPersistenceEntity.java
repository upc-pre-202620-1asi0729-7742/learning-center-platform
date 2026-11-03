package com.acme.center.platform.learning.infrastructure.persistence.jpa.entities;

import com.acme.center.platform.learning.domain.model.valueobjects.TutorialId;
import com.acme.center.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for learning path items.
 */
@Entity
@Table(name = "learning_path_items")
@Getter
@Setter
@NoArgsConstructor
public class LearningPathItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CoursePersistenceEntity course;

    @Embedded
    @AttributeOverride(name = "tutorialId", column = @Column(name = "tutorial_id", nullable = false))
    private TutorialId tutorialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_item_id")
    private LearningPathItemPersistenceEntity nextItem;
}

