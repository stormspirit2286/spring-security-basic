package vn.duynv.springsecuritybasic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String username;
    private List<String> roles;
    private final String tokenType = "Bearer";
}
