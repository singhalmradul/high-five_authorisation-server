package io.github.singhalmradul.authorizationserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.authorizationserver.exceptions.PasswordMismatchException;
import io.github.singhalmradul.authorizationserver.model.SignUpUser;
import io.github.singhalmradul.authorizationserver.model.user.User;
import io.github.singhalmradul.authorizationserver.repositories.user.UserRepository;
import lombok.AllArgsConstructor;

@Service("jpaUserDetailsService")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JpaUserDetailsServiceImpl implements UserDetailsService, JpaUserDetailsService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {

        return repository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public User saveUserWithDetials(SignUpUser signUpUser) {

        if (signUpUser.isPasswordsMismatch()) {
            throw new PasswordMismatchException("passwords don't match");
        }
        if (repository.existsByEmail(signUpUser.getEmail())) {
            throw new UsernameNotFoundException("account with this email already exists");
        }
        var user = User.builder()
                .email(signUpUser.getEmail())
                .passwordEncoder(passwordEncoder::encode)
                .password(signUpUser.getPassword())
                // .roles("USER")
                .build();

        return repository.save(user);
    }
}
