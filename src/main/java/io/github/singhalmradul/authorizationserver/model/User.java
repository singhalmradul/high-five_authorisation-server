package io.github.singhalmradul.authorizationserver.model;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.github.singhalmradul.authorizationserver.model.shared.UserAccountDetails;
import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;
import io.github.singhalmradul.authorizationserver.utilities.UserJsonDeserializer;
import io.github.singhalmradul.authorizationserver.utilities.UserJsonSerializer;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @Type(value = User.class, name = "user")
})
@JsonSerialize(using = UserJsonSerializer.class)
@JsonDeserialize(using = UserJsonDeserializer.class)
public record User(UserAccountDetails accountDetails, UserAuthenticationDetails authenticationDetails) implements UserDetails, CredentialsContainer {

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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authenticationDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return authenticationDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return accountDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authenticationDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authenticationDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authenticationDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return authenticationDetails.isEnabled();
    }

    public UUID getId() {
        return accountDetails.getUserId();
    }

    public String getEmail() {
        return accountDetails.getEmail();
    }
}