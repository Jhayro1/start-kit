package com.winkstec.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_session", indexes = {
        @Index(name = "idx_user_session_user", columnList = "user_id"),
        @Index(name = "idx_user_session_created_at", columnList = "created_at"),
        @Index(name = "idx_user_session_last_seen", columnList = "last_seen_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            columnDefinition = "uuid",
            foreignKey = @ForeignKey(name = "fk_session_user")
    )
    private User user;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 300)
    private String userAgent;

    @Column(name = "location", length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_method", length = 30, nullable = false)
    private UserLoginAudit.LoginMethod loginMethod;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone")
    private ZonedDateTime createdAt;

    @Column(name = "last_seen_at", columnDefinition = "timestamp with time zone")
    private ZonedDateTime lastSeenAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "revoked_at", columnDefinition = "timestamp with time zone")
    private ZonedDateTime revokedAt;
}
