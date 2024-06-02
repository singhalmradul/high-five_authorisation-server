package io.github.singhalmradul.authorizationserver.repositories.oidc;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.singhalmradul.authorizationserver.model.oidc.AuthorizationConsent;

@Repository
public interface AuthorizationConsentRepository
        extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(
        String registeredClientId,
        String principalName
    );

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
