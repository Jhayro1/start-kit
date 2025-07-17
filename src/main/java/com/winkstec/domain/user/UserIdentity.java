package com.winkstec.domain.user;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_identities", uniqueConstraints = {
        @UniqueConstraint(name = "uq_provider_user_id", columnNames = {"provider", "providerUserId"}),
        @UniqueConstraint(name = "uq_user_provider", columnNames = {"user_id", "provider"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "uuid")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Provider provider; // Ej: GOOGLE, PASSWORD, MAGIC_LINK

    @Column(length = 180, nullable = false)
    private String providerUserId; // correo, googleId, etc.

    @Column(name = "secret_hash")
    private String secretHash; // bcrypt si es login con contraseña

    @Lob
    @Column(name = "metadata")
    private String metadata; // JSON plano (ej: avatar, name, locale)

    @Column(name = "last_used_at")
    private ZonedDateTime lastUsedAt;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    public enum Provider {
        PASSWORD,
        GOOGLE,
        GITHUB,
        APPLE,
        MAGIC_LINK,
        FACEBOOK,
        WEB3
    }

}
