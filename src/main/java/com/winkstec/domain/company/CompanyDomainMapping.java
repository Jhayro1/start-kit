package com.winkstec.domain.company;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "company_domain_mappings",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_company_custom_domain", columnNames = "custom_domain")
        },
        indexes = {
                @Index(name = "idx_domain_mapping_company_id", columnList = "company_id"),
                @Index(name = "idx_domain_mapping_is_verified", columnList = "is_verified"),
                @Index(name = "idx_domain_mapping_verified_at", columnList = "verified_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDomainMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            nullable = false,
            columnDefinition = "uuid",
            foreignKey = @ForeignKey(name = "fk_domain_mapping_company")
    )
    private Company company;

    @Column(name = "custom_domain", nullable = false, length = 150)
    private String customDomain;

    @Column(name = "verification_token", length = 100)
    private String verificationToken;

    @Column(name = "dns_record_type", length = 20)
    private String dnsRecordType;

    @Column(name = "verified_at")
    private ZonedDateTime verifiedAt;

    @Builder.Default
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;
}
