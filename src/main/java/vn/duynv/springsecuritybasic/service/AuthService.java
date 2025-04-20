package vn.duynv.springsecuritybasic.service;

import vn.duynv.springsecuritybasic.dto.request.LoginRequest;
import vn.duynv.springsecuritybasic.dto.request.SignupRequest;
import vn.duynv.springsecuritybasic.dto.response.JwtResponse;
import vn.duynv.springsecuritybasic.dto.response.MessageResponse;
import vn.duynv.springsecuritybasic.dto.response.TokenRefreshResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signupRequest);
    TokenRefreshResponse refreshToken(String refreshToken);
    MessageResponse logoutUser(String username);
}
