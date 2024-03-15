package io.github.singhalmradul.authorizationserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.authorizationserver.exceptions.PasswordMismatchException;
import io.github.singhalmradul.authorizationserver.model.SignUpUser;
import io.github.singhalmradul.authorizationserver.model.User;
import io.github.singhalmradul.authorizationserver.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service("jpaUserDetailsService")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsernameOrEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User saveUserWithDetials(SignUpUser signUpUser) {

        if(userRepository.existsByEmail(signUpUser.getEmail())) {
            throw new UsernameNotFoundException("account with this email already exists");
        }

        if (userRepository.existsByUsername(signUpUser.getUsername())) {
            throw new UsernameNotFoundException("username is not available");
        }
        if (!signUpUser.doesPasswordsMatch()) {
            throw new PasswordMismatchException("passwords don't match");
        }
        User user = User.builder()
                .username(signUpUser.getUsername())
                .email(signUpUser.getEmail())
                .password(signUpUser.getPassword())
                .passwordEncoder(passwordEncoder::encode)
                .build();

        userRepository.save(user);

        return user;
    }
}
