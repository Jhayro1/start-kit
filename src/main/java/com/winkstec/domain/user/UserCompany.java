package com.winkstec.domain.user;

import com.winkstec.domain.common.AuditableEntity;
import com.winkstec.domain.company.Company;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "user_companies",
        uniqueConstraints = @UniqueConstraint(name = "unique_user_company", columnNames = {"user_id", "company_id"}),
        indexes = {
                @Index(name = "idx_user_company_user", columnList = "user_id"),
                @Index(name = "idx_user_company_company", columnList = "company_id"),
                @Index(name = "idx_user_companies_created_at", columnList = "created_at"),
                @Index(name = "idx_user_companies_updated_at", columnList = "updated_at"),
                @Index(name = "idx_user_companies_deleted_at", columnList = "deleted_at")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserCompany extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false, columnDefinition = "uuid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",  nullable = false, columnDefinition = "uuid")
    private Company company;

    @Builder.Default
    @Column(name = "is_owner", nullable = false)
    private boolean isOwner = false;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
