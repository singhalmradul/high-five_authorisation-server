// package io.github.singhalmradul.authorizationserver.model;

// import static jakarta.persistence.GenerationType.UUID;

// import java.util.Collection;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// // @Entity
// @Table(name = "user_data")
// @Getter
// @Setter(AccessLevel.PRIVATE)
// @NoArgsConstructor
// public class User implements UserDetails {

//     @Id
//     @GeneratedValue(strategy = UUID)
//     private UUID id;

//     @Column(unique = true, nullable = false)
//     private String username;

//     @Column(unique = true, nullable = false)
//     private String email;

//     @Column(nullable = false)
//     private String password;

//     private List<? extends GrantedAuthority> authorities;

//     private boolean accountNonExpired;

//     private boolean accountNonLocked;

//     private boolean credentialsNonExpired;

//     private boolean enabled;

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return authorities;
//     }

//     @Override
//     public String getPassword() {
//         return password;
//     }

//     @Override
//     public String getUsername() {
//         return username;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return accountNonExpired;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return accountNonLocked;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return credentialsNonExpired;
//     }

//     @Override
//     public boolean isEnabled() {
//         return enabled;
//     }

//     public static Builder builder() {
//         return new Builder();
//     }

//     private static class Builder {
//         private User user;

//         public Builder() {
//             user = new User();
//         }

//         public Builder withId(UUID id) {
//             user.id = id;
//             return this;
//         }

//         public Builder withUsername(String username) {
//             user.setUsername(username);
//             return this;
//         }

//         public Builder withEmail(String email) {
//             user.setEmail(email);
//             return this;
//         }

//         public Builder withPassword(String password) {
//             user.setPassword(password);
//             return this;
//         }

//         public Builder withAuthorities(List<? extends GrantedAuthority> authorities) {
//             user.setAuthorities(authorities);
//             return this;
//         }

//         public Builder withAccountNonExpired(boolean accountNonExpired) {
//             user.setAccountNonExpired(accountNonExpired);
//             return this;
//         }

//         public Builder withAccountNonLocked(boolean accountNonLocked) {
//             user.setAccountNonLocked(accountNonLocked);
//             return this;
//         }

//         public Builder withCredentialsNonExpired(boolean credentialsNonExpired) {
//             user.setCredentialsNonExpired(credentialsNonExpired);
//             return this;
//         }

//         public Builder withEnabled(boolean enabled) {
//             user.setEnabled(enabled);
//             return this;
//         }

//         public User build() {
//             return user;
//         }

//     }
// }
