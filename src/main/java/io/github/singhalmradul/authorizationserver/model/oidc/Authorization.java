package io.github.singhalmradul.authorizationserver.model.oidc;

import static jakarta.persistence.GenerationType.UUID;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "oidc_authorization")
@Data
public class Authorization {

    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "registered_client_id", nullable = false)
    private String registeredClientId;

    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @Column(name = "authorization_grant_type", nullable = false)
    private String authorizationGrantType;

    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    @Column(name = "attributes", length = 4000)
    private String attributes;

    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorization_code_value", length = 4000)
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "authorization_code_metadata", length = 2000)
    private String authorizationCodeMetadata;

    @Column(name = "access_token_value", length = 4000)
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Column(name = "access_token_metadata", length = 2000)
    private String accessTokenMetadata;

    @Column(name = "access_token_type")
    private String accessTokenType;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "refresh_token_value", length = 4000)
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    @Column(name = "refresh_token_metadata", length = 2000)
    private String refreshTokenMetadata;

    @Column(name = "oidc_id_token_value", length = 4000)
    private String oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;

    @Column(name = "oidc_id_token_metadata", length = 2000)
    private String oidcIdTokenMetadata;

    @Column(name = "oidc_id_token_claims", length = 2000)
    private String oidcIdTokenClaims;

    @Column(name = "user_code_value", length = 4000)
    private String userCodeValue;

    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;

    @Column(name = "user_code_metadata", length = 2000)
    private String userCodeMetadata;

    @Column(name = "device_code_value", length = 4000)
    private String deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;

    @Column(name = "device_code_metadata", length = 2000)
    private String deviceCodeMetadata;
}