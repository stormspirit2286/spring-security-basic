package vn.duynv.springsecuritybasic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duynv.springsecuritybasic.dto.request.CreateUserRequest;
import vn.duynv.springsecuritybasic.dto.response.UserResponseDTO;
import vn.duynv.springsecuritybasic.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequest request) {
        UserResponseDTO result = userService.createUser(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
