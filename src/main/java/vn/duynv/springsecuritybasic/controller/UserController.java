package vn.duynv.springsecuritybasic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duynv.springsecuritybasic.dto.request.CreateUserRequest;
import vn.duynv.springsecuritybasic.dto.response.UserResponseDTO;
import vn.duynv.springsecuritybasic.service.UserService;

import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> results = userService.getUsers();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
