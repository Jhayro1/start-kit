package com.winkstec.service.auth.impl;

import com.winkstec.domain.user.User;
import com.winkstec.domain.user.UserIdentity;
import com.winkstec.domain.user.UserLoginAudit;
import com.winkstec.dto.request.auth.*;
import com.winkstec.dto.request.email.EmailRequest;
import com.winkstec.dto.response.auth.LoginResponse;
import com.winkstec.dto.response.auth.TokenRefreshResponse;
import com.winkstec.exception.ApiException;
import com.winkstec.exception.code.AuthErrorCode;
import com.winkstec.redis.RedisKeyBuilder;
import com.winkstec.redis.RedisService;
import com.winkstec.redis.core.RedisKeyPrefix;
import com.winkstec.repository.UserRepository;
import com.winkstec.repository.jpa.UserIdentityRepository;
import com.winkstec.security.jwt.JwtService;
import com.winkstec.security.jwt.RefreshTokenService;
import com.winkstec.service.auth.AuthService;
import com.winkstec.service.email.EmailComposerService;
import com.winkstec.service.email.EmailQueueService;
import com.winkstec.service.security.LoginAuditService;
import com.winkstec.util.RandomCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserIdentityRepository userIdentityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final LoginAuditService loginAuditService;
    private final EmailQueueService emailQueueService;
    private final EmailComposerService emailComposerService;
    private final RedisService redisService;

    @Value("${winkstec.auth.refresh-token-ttl:10080}")
    private long refreshTokenTtlMinutes;

    private static final Duration REGISTRATION_TOKEN_TTL = Duration.ofMinutes(15);

    @Override
    public void register(RegisterRequest request) {
        if (userIdentityRepository.existsByProviderAndProviderUserId(UserIdentity.Provider.PASSWORD, request.getEmail())) {
            throw new ApiException(AuthErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        String token = UUID.randomUUID().toString();

        redisService.save(
                RedisKeyBuilder.build(RedisKeyPrefix.REGISTRATION_TOKEN, token),
                request,
                REGISTRATION_TOKEN_TTL
        );

        sendRegistrationEmail(request.getEmail(), token);
    }

    @Override
    @Transactional
    public void confirmRegistration(String token) {
        String key = RedisKeyBuilder.build(RedisKeyPrefix.REGISTRATION_TOKEN, token);
        RegisterRequest request = redisService.get(key, RegisterRequest.class);
        if (request == null) {
            throw new ApiException(AuthErrorCode.INVALID_OR_EXPIRED_TOKEN);
        }

        if (userIdentityRepository.existsByProviderAndProviderUserId(UserIdentity.Provider.PASSWORD, request.getEmail())) {
            throw new ApiException(AuthErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        ZonedDateTime nowUtc = ZonedDateTime.now();
        User user = createUser(request.getEmail());
        createUserIdentity(user, request.getPassword(), nowUtc);

        user.setVerifiedEmail(true);
        user.setActive(true);
        userRepository.save(user);

        redisService.delete(key);
    }

    @Override
    @Transactional
    public void completeProfile(CompleteProfileRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(AuthErrorCode.USER_NOT_FOUND));

        if (!user.isVerifiedEmail()) {
            throw new ApiException(AuthErrorCode.USER_NOT_VERIFIED);
        }

        user.setFirstName(request.getFirstName());
        user.setLastNameFather(request.getLastNameFather());
        user.setLastNameMother(request.getLastNameMother());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBirthday(request.getBirthday());
        user.setTimezone(request.getTimezone());
        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        UserIdentity identity = userIdentityRepository
                .findByProviderAndProviderUserId(UserIdentity.Provider.PASSWORD, request.getEmail())
                .orElseThrow(() -> new ApiException(AuthErrorCode.INVALID_CREDENTIALS));

        User user = identity.getUser();

        if (!passwordEncoder.matches(request.getPassword(), identity.getSecretHash())) {
            throw new ApiException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        if (!user.isVerifiedEmail()) {
            throw new ApiException(AuthErrorCode.USER_NOT_VERIFIED);
        }

        if (!user.isActive()) {
            throw new ApiException(AuthErrorCode.FORBIDDEN);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.save(user.getId(), refreshToken, refreshTokenTtlMinutes);
        loginAuditService.registerSuccess(user, UserLoginAudit.LoginMethod.PASSWORD);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String token = request.getRefreshToken();

        if (!jwtService.validateRefreshToken(token)) {
            throw new ApiException(AuthErrorCode.UNAUTHORIZED);
        }

        UUID userId = jwtService.getUserIdFromToken(token);

        if (!refreshTokenService.isValid(userId, token)) {
            throw new ApiException(AuthErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(AuthErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.save(user.getId(), newRefreshToken, refreshTokenTtlMinutes);

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    // ======================
    // Métodos privados SOLID
    // ======================

    private User createUser(String email) {
        return userRepository.save(
                User.builder()
                        .email(email)
                        .isVerifiedEmail(false)
                        .isActive(false)
                        .build()
        );
    }

    private void createUserIdentity(User user, String rawPassword, ZonedDateTime nowUtc) {
        UserIdentity identity = UserIdentity.builder()
                .user(user)
                .provider(UserIdentity.Provider.PASSWORD)
                .providerUserId(user.getEmail())
                .secretHash(passwordEncoder.encode(rawPassword))
                .createdAt(nowUtc)
                .build();
        userIdentityRepository.save(identity);
    }

    private void sendRegistrationEmail(String email, String token) {
        String confirmLink = "https://winkstec.com/auth/confirm-registration?token=" + token;

        Map<String, Object> data = Map.of(
                "empresaNombre", "Winkstec",
                "logoUrl", "https://winkstec.com/logo.png",
                "confirmLink", confirmLink,
                "facebookUrl", "https://facebook.com/winkstec",
                "instagramUrl", "https://instagram.com/winkstec"
        );

        String html = emailComposerService.compose("registration-link", data);

        EmailRequest emailRequest = EmailRequest.builder()
                .trackingId(UUID.randomUUID())
                .to(List.of(email))
                .subject("Activa tu cuenta en Winkstec")
                .body(html)
                .isHtml(true)
                .maxRetries(3)
                .build();

        emailQueueService.registrarEmail(emailRequest);
    }
}

