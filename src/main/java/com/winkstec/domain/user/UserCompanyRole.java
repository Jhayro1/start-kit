package com.winkstec.domain.user;

import com.winkstec.domain.common.AuditableEntity;
import com.winkstec.domain.security.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_company_roles",
        uniqueConstraints = @UniqueConstraint(name = "unique_user_company_role", columnNames = {"user_company_id", "role_id"}),
        indexes = {
                @Index(name = "idx_user_company_roles_usercompany", columnList = "user_company_id"),
                @Index(name = "idx_user_company_roles_role", columnList = "role_id"),
                @Index(name = "idx_user_company_roles_created_at", columnList = "created_at"),
                @Index(name = "idx_user_company_roles_updated_at", columnList = "updated_at"),
                @Index(name = "idx_user_company_roles_deleted_at", columnList = "deleted_at")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserCompanyRole extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_company_id", nullable = false, columnDefinition = "uuid")
    private UserCompany userCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "uuid")
    private Role role;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
