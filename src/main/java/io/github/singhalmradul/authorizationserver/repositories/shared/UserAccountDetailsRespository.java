package io.github.singhalmradul.authorizationserver.repositories.shared;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.singhalmradul.authorizationserver.model.shared.UserAccountDetails;

public interface UserAccountDetailsRespository extends JpaRepository<UserAccountDetails, UUID> {

    @Query("""
        SELECT a
        FROM UserAccountDetails a
        WHERE a.username = :value
            OR a.email = :value
    """)
    Optional<UserAccountDetails> findByUsernameOrEmail(@Param("value") String usernameOrEmail);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}