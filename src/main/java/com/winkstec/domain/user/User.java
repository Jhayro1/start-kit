package com.winkstec.domain.user;

import com.winkstec.domain.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_created_at", columnList = "created_at"),
        @Index(name = "idx_users_updated_at", columnList = "updated_at"),
        @Index(name = "idx_users_deleted_at", columnList = "deleted_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 150, unique = true)
    private String email; // Ya no es obligatorio porque puede venir de identidad externa

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name_father", length = 100)
    private String lastNameFather;

    @Column(name = "last_name_mother", length = 100)
    private String lastNameMother;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "default_company_id", columnDefinition = "CHAR(36)")
    private UUID defaultCompanyId;

    @Builder.Default
    @Column(name = "is_verified_email", nullable = false)
    private boolean isVerifiedEmail = false;

    @Builder.Default
    @Column(name = "is_system_admin", nullable = false)
    private boolean isSystemAdmin = false;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @Column(length = 50)
    private String timezone;

    @Column(length = 10)
    private String verificationCode;

    private ZonedDateTime verificationExpiresAt;

    @Column(name = "birthday")
    private ZonedDateTime birthday;
}
