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
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize(builder = User.UserBuilder.class)
@Builder(builderClassName = "UserBuilder", setterPrefix = "with")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @Transient
    @Builder.Default
    private transient UnaryOperator<String> passwordEncoder = UnaryOperator.identity();

    @ManyToMany(targetEntity = Authority.class, cascade = { MERGE, PERSIST }, fetch = EAGER)
    @JoinTable(
        name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    @JsonSerialize(converter = PersistentBagConverter.class)
    private Collection<Authority> authorities;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean accountNonLocked = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Builder.Default
    private boolean enabled = true;

    // ---------------------------------------------------------------------------------------------------
    public User(UUID id,
        String username,
        String email,
        String password,
        UnaryOperator<String> passwordEncoder,
        Collection<Authority> authorities,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordEncoder = passwordEncoder;
        this.password = passwordEncoder.apply(password);
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    // ---------------------------------------------------------------------------------------------------
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
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;
        private UnaryOperator<String> passwordEncoder;

        private UserBuilder() {
            passwordEncoder = UnaryOperator.identity();
        }

        public UserBuilder withRoles(String... roles) {

            List<Authority> authorities = new ArrayList<>(roles.length);

            for (String role : roles) {
                Assert.isTrue(
                    !role.startsWith("ROLE_"),
                    () -> role + " cannot start with ROLE_ (it is automatically added)"
                );
                authorities.add(new Authority("ROLE_" + role));
            }

            return withAuthorities(authorities);
        }

        public UserDetails build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);

            return new User(
                this.id,
                this.username,
                this.email,
                encodedPassword,
                this.passwordEncoder,
                this.authorities,
                this.accountNonExpired,
                this.accountNonLocked,
                this.credentialsNonExpired,
                this.enabled
            );
        }
    }
}