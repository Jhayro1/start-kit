package com.winkstec.security.jwt;

import com.winkstec.domain.user.User;
import com.winkstec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshService;
    private final UserRepository userRepository;
    private final JwtProperties props;


    public Optional<User> getUserFromAccessToken(String token) {
        if (!tokenProvider.isValid(token)) return Optional.empty();
        return userRepository.findById(tokenProvider.getUserId(token));
    }

    public String generateAccessToken(User user) {
        return tokenProvider.generateAccessToken(user.getId());
    }

    public String generateRefreshToken(User user) {
        String token = tokenProvider.generateRefreshToken(user.getId());
        long minutes = props.getRefreshTokenExpirationSeconds() / 60;
        refreshService.save(user.getId(), token, minutes);
        return token;
    }

    public boolean validateRefreshToken(String token) {
        if (!tokenProvider.isValid(token)) return false;
        UUID userId = tokenProvider.getUserId(token);
        return refreshService.isValid(userId, token);
    }

    public UUID getUserIdFromToken(String token) {
        return tokenProvider.getUserId(token);
    }

    public void revokeRefreshToken(UUID userId) {
        refreshService.revoke(userId);
    }
}
