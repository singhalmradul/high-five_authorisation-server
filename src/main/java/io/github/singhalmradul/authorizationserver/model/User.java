package io.github.singhalmradul.authorizationserver.model;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import io.github.singhalmradul.authorizationserver.model.shared.UserAccountDetails;
import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;

public record User(UserAccountDetails accountDetails, UserAuthenticationDetails authenticationDetails) implements CredentialsContainer {

    @Override
    public void eraseCredentials() {
        authenticationDetails.eraseCredentials();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [");
        builder.append("username=").append(accountDetails.getUsername()).append(", ");
        builder.append("email=").append(accountDetails.getEmail());
        builder.append("]");
        return builder.toString();
    }

    public UUID getId() {
        return accountDetails.getUserId();
    }

    public String getEmail() {
        return accountDetails.getEmail();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authenticationDetails.getAuthorities();
    }

    public String getPassword() {
        return authenticationDetails.getPassword();
    }

    public String getUsername() {
        return accountDetails.getUsername();
    }

    public boolean isAccountNonExpired() {
        return authenticationDetails.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return authenticationDetails.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return authenticationDetails.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return authenticationDetails.isEnabled();
    }

}