package com.winkstec.domain.company;

import com.winkstec.domain.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "companies", indexes = {
        @Index(name = "idx_companies_name", columnList = "name"),
        @Index(name = "idx_companies_slug", columnList = "slug_subdomain", unique = true),
        @Index(name = "idx_companies_verification_status", columnList = "verification_status"),
        @Index(name = "idx_companies_created_at", columnList = "created_at"),
        @Index(name = "idx_companies_updated_at", columnList = "updated_at"),
        @Index(name = "idx_companies_deleted_at", columnList = "deleted_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(name = "slug_subdomain", length = 100, unique = true, nullable = false)
    private String slugSubdomain;

    @Column(length = 2)
    private String country;

    @Lob
    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "description")
    private String description;

    @Builder.Default
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(name = "verified_at")
    private ZonedDateTime verifiedAt;

    @Lob
    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Builder.Default
    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @Column(length = 50)
    private String timezone;

    public enum VerificationStatus {
        PENDING, APPROVED, REJECTED
    }
}
