package vn.duynv.springsecuritybasic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.duynv.springsecuritybasic.config.JwtTokenProvide;
import vn.duynv.springsecuritybasic.dto.request.LoginRequest;
import vn.duynv.springsecuritybasic.dto.request.SignupRequest;
import vn.duynv.springsecuritybasic.dto.response.JwtResponse;
import vn.duynv.springsecuritybasic.dto.response.MessageResponse;
import vn.duynv.springsecuritybasic.dto.response.TokenRefreshResponse;
import vn.duynv.springsecuritybasic.entity.RefreshToken;
import vn.duynv.springsecuritybasic.entity.User;
import vn.duynv.springsecuritybasic.repository.RefreshTokenRepository;
import vn.duynv.springsecuritybasic.repository.UserRepository;
import vn.duynv.springsecuritybasic.service.AuthService;
import vn.duynv.springsecuritybasic.service.RefreshTokenService;
import vn.duynv.springsecuritybasic.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvide tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .filter(authority -> authority.getAuthority().startsWith("ROLE_"))
                .map(authority -> authority.getAuthority().substring(5))
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

        return new JwtResponse(
                jwt,
                refreshToken.getToken(),
                userDetails.getUsername(),
                roles
        );
    }

    @Override
    public MessageResponse registerUser(SignupRequest signupRequest) {
        return null;
    }

    @Override
    public TokenRefreshResponse refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public MessageResponse logoutUser(String username) {
        return null;
    }

    private RefreshToken createRefreshToken(User user) {
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(user.getId());
        existingToken.ifPresent(refreshTokenRepository::delete);
        Instant expiryDate = Instant.now().plusMillis(604800000); // 7 days
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(expiryDate)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
}
