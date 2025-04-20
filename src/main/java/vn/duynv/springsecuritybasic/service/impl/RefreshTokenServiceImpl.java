package vn.duynv.springsecuritybasic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.duynv.springsecuritybasic.entity.RefreshToken;
import vn.duynv.springsecuritybasic.entity.User;
import vn.duynv.springsecuritybasic.repository.RefreshTokenRepository;
import vn.duynv.springsecuritybasic.repository.UserRepository;
import vn.duynv.springsecuritybasic.service.RefreshTokenService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Delete existing refresh token if any
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.isExpired()) {
//            refreshTokenRepository.delete(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired");
//        }

        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.findByUserId(userId)
                .ifPresent(refreshTokenRepository::delete);
    }
}
