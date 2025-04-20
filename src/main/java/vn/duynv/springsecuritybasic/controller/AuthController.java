package vn.duynv.springsecuritybasic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duynv.springsecuritybasic.dto.request.LoginRequest;
import vn.duynv.springsecuritybasic.dto.response.JwtResponse;
import vn.duynv.springsecuritybasic.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authService.authenticateUser(request), HttpStatus.OK);
    }
}
