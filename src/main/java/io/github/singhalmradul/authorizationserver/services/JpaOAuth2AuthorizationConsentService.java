package io.github.singhalmradul.authorizationserver.services;

import static org.springframework.util.StringUtils.commaDelimitedListToSet;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import io.github.singhalmradul.authorizationserver.model.oidc.AuthorizationConsent;
import io.github.singhalmradul.authorizationserver.repositories.oidc.AuthorizationConsentRepository;

@Component
public class JpaOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {
    private final AuthorizationConsentRepository authorizationConsentRepository;
    private final RegisteredClientRepository registeredClientRepository;

    public JpaOAuth2AuthorizationConsentService(
        AuthorizationConsentRepository authorizationConsentRepository,
        RegisteredClientRepository registeredClientRepository
    ) {

        Assert.notNull(authorizationConsentRepository, "authorizationConsentRepository cannot be null");
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");

        this.authorizationConsentRepository = authorizationConsentRepository;
        this.registeredClientRepository = registeredClientRepository;
    }

    @SuppressWarnings("null")
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {

        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");

        this.authorizationConsentRepository.save(toEntity(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {

        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");

        this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(),
                authorizationConsent.getPrincipalName()
        );
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {

        Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");

        return authorizationConsentRepository
            .findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName)
            .map(this::toObject)
            .orElse(null);
    }

    private OAuth2AuthorizationConsent toObject(AuthorizationConsent authorizationConsent) {

        String registeredClientId = authorizationConsent.getRegisteredClientId();
        RegisteredClient registeredClient = registeredClientRepository.findById(registeredClientId);

        if (registeredClient == null) {

            throw new DataRetrievalFailureException(
                "The RegisteredClient with id '"
                + registeredClientId
                + "' was not found in the RegisteredClientRepository."
            );
        }

        @SuppressWarnings("null")
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
            registeredClientId,
            authorizationConsent.getPrincipalName()
        );

        if (authorizationConsent.getAuthorities() != null) {

            for (String authority : commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
        }

        return builder.build();
    }

    private AuthorizationConsent toEntity(OAuth2AuthorizationConsent authorizationConsent) {

        AuthorizationConsent entity = new AuthorizationConsent();
        entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
        entity.setPrincipalName(authorizationConsent.getPrincipalName());

        Set<String> authorities = new HashSet<>();

        for (GrantedAuthority authority : authorizationConsent.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }

        entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));

        return entity;
    }
}