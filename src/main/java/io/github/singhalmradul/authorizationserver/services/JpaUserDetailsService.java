package io.github.singhalmradul.authorizationserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.authorizationserver.exceptions.PasswordMismatchException;
import io.github.singhalmradul.authorizationserver.model.SignUpUser;
import io.github.singhalmradul.authorizationserver.model.User;
import io.github.singhalmradul.authorizationserver.model.shared.UserAccountDetails;
import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;
import io.github.singhalmradul.authorizationserver.repositories.shared.UserAccountDetailsRespository;
import io.github.singhalmradul.authorizationserver.repositories.user.UserAuthenticationDetailsRepository;
import lombok.AllArgsConstructor;

@Service("jpaUserDetailsService")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JpaUserDetailsService implements UserDetailsService {

    private UserAccountDetailsRespository accountDetailsRespository;
    private UserAuthenticationDetailsRepository authenticationDetailsRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        var accountDetails = accountDetailsRespository.findByUsernameOrEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username: " + username));
        var authenticationDetails = authenticationDetailsRepository.findById(accountDetails.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(accountDetails, authenticationDetails);
    }

    public User saveUserWithDetials(SignUpUser signUpUser) {

        if (!signUpUser.doesPasswordsMatch()) {
            throw new PasswordMismatchException("passwords don't match");
        }
        if(accountDetailsRespository.existsByEmail(signUpUser.getEmail())) {
            throw new UsernameNotFoundException("account with this email already exists");
        }

        if (accountDetailsRespository.existsByUsername(signUpUser.getUsername())) {
            throw new UsernameNotFoundException("username is not available");
        }

        var accountDetails = UserAccountDetails.builder()
            .username(signUpUser.getUsername())
            .email(signUpUser.getEmail())
            .build();

        var authenticationDetails = UserAuthenticationDetails.builder()
            .passwordEncoder(passwordEncoder::encode)
            .password(signUpUser.getPassword())
            .roles("USER")
            .build();

        return new User(accountDetails, authenticationDetails);
    }
}
