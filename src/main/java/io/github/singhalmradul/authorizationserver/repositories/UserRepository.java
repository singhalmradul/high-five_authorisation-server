package io.github.singhalmradul.authorizationserver.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.singhalmradul.authorizationserver.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
        SELECT user
        FROM User user
        WHERE user.username = :value
            OR user.email = :value
    """)
    Optional<User> findByUsernameOrEmail(@Param("value") String usernameOrEmail);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
