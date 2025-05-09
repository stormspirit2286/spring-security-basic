package vn.duynv.springsecuritybasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.duynv.springsecuritybasic.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
