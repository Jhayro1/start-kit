package com.winkstec.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_login_audit", indexes = {
        @Index(name = "idx_login_user", columnList = "user_id"),
        @Index(name = "idx_login_method", columnList = "login_method"),
        @Index(name = "idx_login_success", columnList = "success"),
        @Index(name = "idx_login_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            columnDefinition = "uuid",
            foreignKey = @ForeignKey(name = "fk_login_user")
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_method", length = 30, nullable = false)
    private LoginMethod loginMethod;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 300)
    private String userAgent;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "success", nullable = false)
    private boolean success;

    @Column(name = "failure_reason", length = 150)
    private String failureReason;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone")
    private ZonedDateTime createdAt;

    public enum LoginMethod {
        PASSWORD, GOOGLE, FACEBOOK, APPLE, GITHUB, MAGIC_LINK, WEB3
    }
}
