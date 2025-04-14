package vn.duynv.springsecuritybasic.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Set<String> roles;
    private boolean active;
    private LocalDateTime createdAt;
}
