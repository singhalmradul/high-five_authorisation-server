package io.github.singhalmradul.authorizationserver.model.user;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.UUID;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.UnaryOperator;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.github.singhalmradul.authorizationserver.utilities.PersistentBagConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_authentication_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
public class UserAuthenticationDetails implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @JsonIgnore
    @Column(name = "password", length = 68, nullable = false)
    private String password;

    @ManyToMany(targetEntity = Authority.class, cascade = { MERGE, PERSIST }, fetch = EAGER)
    @JoinTable(
        name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    @JsonSerialize(converter = PersistentBagConverter.class)
    private Collection<Authority> authorities;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    // -------------------------------------------------------------------------------------------------
    @Override
    public String getUsername() {
        return userId.toString();
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    // -------------------------------------------------------------------------------------------------
    public static final class UserAuthenticationDetailsBuilder {

        private UUID id;
        private String password;
        private Collection<Authority> authorities;
        private boolean accountNonExpired = true;
        private boolean accountNonLocked = true;
        private boolean credentialsNonExpired = true;
        private boolean enabled = true;
        private UnaryOperator<String> passwordEncoder;

        UserAuthenticationDetailsBuilder() {
            passwordEncoder = UnaryOperator.identity();
        }

        public UserAuthenticationDetailsBuilder authorities(Collection<Authority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserAuthenticationDetailsBuilder authorities(Authority... authorities) {
            return authorities(asList(authorities));
        }

        public UserAuthenticationDetailsBuilder roles(String... roles) {

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

        public UserAuthenticationDetailsBuilder passwordEncoder(UnaryOperator<String> passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return this;
        }

        public UserAuthenticationDetails build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);
            SortedSet<Authority> sortedAuthorities = new TreeSet<>(
                (a, b) -> a != null ? a.compareTo(b) : -1
            );
            sortedAuthorities.addAll(this.authorities);

            return new UserAuthenticationDetails(
                this.id,
                encodedPassword,
                sortedAuthorities, // specified by UserDetails interface
                this.accountNonExpired,
                this.accountNonLocked,
                this.credentialsNonExpired,
                this.enabled
            );
        }
    }
}