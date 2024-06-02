package io.github.singhalmradul.authorizationserver.repositories.oidc;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.singhalmradul.authorizationserver.model.oidc.Authorization;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, UUID> {
    Optional<Authorization> findByState(String state);

    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);

    Optional<Authorization> findByAccessTokenValue(String accessToken);

    Optional<Authorization> findByRefreshTokenValue(String refreshToken);

    Optional<Authorization> findByOidcIdTokenValue(String idToken);

    Optional<Authorization> findByUserCodeValue(String userCode);

    Optional<Authorization> findByDeviceCodeValue(String deviceCode);

    @Query("""
        SELECT a
        FROM Authorization a
        WHERE a.state = :token
            OR a.authorizationCodeValue = :token
            OR a.accessTokenValue = :token
            OR a.refreshTokenValue = :token
            OR a.oidcIdTokenValue = :token
            OR a.userCodeValue = :token
            OR a.deviceCodeValue = :token
    """)
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(
        @Param("token") String token
    );
}