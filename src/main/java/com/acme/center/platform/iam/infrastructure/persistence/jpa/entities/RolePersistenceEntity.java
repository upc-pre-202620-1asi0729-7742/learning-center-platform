package com.acme.center.platform.iam.infrastructure.persistence.jpa.entities;

import com.acme.center.platform.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for IAM roles.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class RolePersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private Roles name;
}
