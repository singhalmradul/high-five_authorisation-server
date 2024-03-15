package io.github.singhalmradul.authorizationserver.model;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.UUID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.github.singhalmradul.authorizationserver.utils.PersistentBagConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
@Builder(builderClassName = "UserBuilder")
public class User implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @Pattern(regexp = "^[a-z][a-z0-9]*$", message = "username must contain only lowercase letters and numbers, begin with a letter")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(targetEntity = Authority.class, cascade = { MERGE, PERSIST }, fetch = EAGER)
    @JoinTable(
        name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    @JsonSerialize(converter = PersistentBagConverter.class)
    private Collection<Authority> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    // ---------------------------------------------------------------------------------------------------
    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [");
        builder.append("username=").append(username).append(", ");
        builder.append("email=").append(email);
        builder.append("]");
        return builder.toString();
    }

    // ---------------------------------------------------------------------------------------------------
    public static final class UserBuilder {


        private UUID id;
        private String username;
        private String email;
        private String password;
        private Collection<Authority> authorities;
        private boolean accountNonExpired = true;
        private boolean accountNonLocked = true;
        private boolean credentialsNonExpired = true;
        private boolean enabled = true;
        private UnaryOperator<String> passwordEncoder;

        private UserBuilder() {
            passwordEncoder = UnaryOperator.identity();
        }

        public UserBuilder roles(String... roles) {

            List<Authority> authorities = new ArrayList<>(roles.length);

            for (String role : roles) {
                Assert.isTrue(
                    !role.startsWith("ROLE_"),
                    () -> role + " cannot start with ROLE_ (it is automatically added)"
                );
                authorities.add(new Authority("ROLE_" + role));
            }

            return authorities(authorities);
        }

        public UserBuilder passwordEncoder(UnaryOperator<String> passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return this;
        }

        public User build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);

            return new User(
                this.id,
                this.username,
                this.email,
                encodedPassword,
                this.authorities,
                this.accountNonExpired,
                this.accountNonLocked,
                this.credentialsNonExpired,
                this.enabled
            );
        }
    }
}