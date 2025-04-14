package vn.duynv.springsecuritybasic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duynv.springsecuritybasic.entity.RefreshToken;
import vn.duynv.springsecuritybasic.entity.User;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(Long userId);
    void deleteByUser(User user);
}
