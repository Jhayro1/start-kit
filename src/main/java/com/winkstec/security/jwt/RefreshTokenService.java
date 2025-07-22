package com.winkstec.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redis;

    public void save(UUID userId, String token, long expirationMinutes) {
        redis.opsForValue().set(buildKey(userId), token, Duration.ofMinutes(expirationMinutes));
    }

    public boolean isValid(UUID userId, String token) {
        String stored = redis.opsForValue().get(buildKey(userId));
        return stored != null && stored.equals(token);
    }

    public void revoke(UUID userId) {
        redis.delete(buildKey(userId));
    }

    private String buildKey(UUID userId) {
        return "refresh:" + userId;
    }
}
