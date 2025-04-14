package vn.duynv.springsecuritybasic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.duynv.springsecuritybasic.entity.Permission;
import vn.duynv.springsecuritybasic.entity.Role;
import vn.duynv.springsecuritybasic.entity.User;
import vn.duynv.springsecuritybasic.repository.PermissionRepository;
import vn.duynv.springsecuritybasic.repository.RoleRepository;
import vn.duynv.springsecuritybasic.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityBasicApplication {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBasicApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            Permission readPermission = createPermissionIfNotFound("READ_CONTENT");
            Permission writePermission = createPermissionIfNotFound("WRITE_CONTENT");
            Permission deletePermission = createPermissionIfNotFound("DELETE_CONTENT");

            Role userRole = createRoleIfNotFound("USER", Set.of(readPermission));
            Role adminRole = createRoleIfNotFound("ADMIN",
                    Set.of(readPermission, writePermission, deletePermission));
            if (!userRepository.existsByUsername("user")) {
                User user = User.builder()
                        .username("user")
                        .email("user@example.com")
                        .password(passwordEncoder.encode("password"))
                        .fullName("Regular User")
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .roles(Set.of(userRole))
                        .build();

                userRepository.save(user);
                log.info("Created user: user@example.com / password");
            }

            if (!userRepository.existsByUsername("admin")) {
                User admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("password"))
                        .fullName("Admin User")
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(admin);
                log.info("Created admin: admin@example.com / password");
            }
        };
    }

    private Permission createPermissionIfNotFound(String name) {
        return permissionRepository.findByName(name).orElseGet(() -> {
            Permission permission = Permission.builder().name(name).build();
            return permissionRepository.save(permission);
        });
    }

    private Role createRoleIfNotFound(String name, Set<Permission> permissions) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .name(name)
                            .permissions(permissions)
                            .build();
                    return roleRepository.save(role);
                });
    }

}
