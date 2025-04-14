package vn.duynv.springsecuritybasic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.duynv.springsecuritybasic.dto.request.CreateUserRequest;
import vn.duynv.springsecuritybasic.dto.response.UserResponseDTO;
import vn.duynv.springsecuritybasic.entity.Role;
import vn.duynv.springsecuritybasic.entity.User;
import vn.duynv.springsecuritybasic.repository.RoleRepository;
import vn.duynv.springsecuritybasic.repository.UserRepository;
import vn.duynv.springsecuritybasic.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(CreateUserRequest createUserRequest) {
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .fullName(createUserRequest.getFullName())
                .active(true)
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();
        if (createUserRequest.getRoles() != null && !createUserRequest.getRoles().isEmpty()) {
            for (String roleName : createUserRequest.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
        } else {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            roles.add(userRole);
        }
        user.setRoles(roles);
        User userCreated = userRepository.save(user);
        return mapUserToDTO(userCreated);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponseDTO getCurrentUserInfo() {
        return null;
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {

    }

    @Override
    public void deleteAccount(String password) {

    }

    private UserResponseDTO mapUserToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
