package vn.duynv.springsecuritybasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.duynv.springsecuritybasic.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    long countByRolesName(String roleName);
}
