package com.winkstec.domain.security;

import com.winkstec.domain.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "permissions", indexes = {
        @Index(name = "idx_permissions_name", columnList = "name", unique = true),
        @Index(name = "idx_permissions_module", columnList = "module"),
        @Index(name = "idx_permissions_created_at", columnList = "created_at"),
        @Index(name = "idx_permissions_updated_at", columnList = "updated_at"),
        @Index(name = "idx_permissions_deleted_at", columnList = "deleted_at")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Permission extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 150, nullable = false, unique = true)
    private String name;

    @Lob
    private String description;

    @Column(length = 100)
    private String module;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
