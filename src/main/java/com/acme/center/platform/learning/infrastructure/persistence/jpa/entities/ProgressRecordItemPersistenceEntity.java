package com.acme.center.platform.learning.infrastructure.persistence.jpa.entities;

import com.acme.center.platform.learning.domain.model.valueobjects.ProgressStatus;
import com.acme.center.platform.learning.domain.model.valueobjects.TutorialId;
import com.acme.center.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * JPA persistence entity for progress record items.
 */
@Entity
@Table(name = "progress_record_items")
@Getter
@Setter
@NoArgsConstructor
public class ProgressRecordItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private EnrollmentPersistenceEntity enrollment;

    @Embedded
    @AttributeOverride(name = "tutorialId", column = @Column(name = "tutorial_id", nullable = false))
    private TutorialId tutorialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStatus status;

    @Column(name = "started_at")
    private Date statedAt;

    @Column(name = "completed_at")
    private Date completedAt;
}

