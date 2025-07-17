package com.winkstec.domain.security;

import com.winkstec.domain.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "roles", indexes = {
        @Index(name = "idx_roles_name", columnList = "name", unique = true),
        @Index(name = "idx_roles_created_at", columnList = "created_at"),
        @Index(name = "idx_roles_updated_at", columnList = "updated_at"),
        @Index(name = "idx_roles_deleted_at", columnList = "deleted_at")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Lob
    private String description;

    @Builder.Default
    private Integer level = 1;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
