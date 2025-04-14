package vn.duynv.springsecuritybasic.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Set<String> roles;
}
