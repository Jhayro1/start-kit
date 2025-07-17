package com.winkstec.domain.security;

import com.winkstec.domain.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "role_permissions",
        uniqueConstraints = @UniqueConstraint(name = "unique_role_permission", columnNames = {"role_id", "permission_id"}),
        indexes = {
                @Index(name = "idx_role_permissions_created_at", columnList = "created_at"),
                @Index(name = "fk_role_permissions_permission", columnList = "permission_id")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RolePermission extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "uuid")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false, columnDefinition = "uuid")
    private Permission permission;
}
