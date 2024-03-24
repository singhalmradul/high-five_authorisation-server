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
public class JpaUserDetailsServiceImpl implements UserDetailsService, JpaUserDetailsService {

    private UserAccountDetailsRespository accountDetailsRespository;
    private UserAuthenticationDetailsRepository authenticationDetailsRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserAuthenticationDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return getByUsernameOrEmail(username);
    }

    @Override
    public UserAuthenticationDetails getByUsernameOrEmail(String username) {
        var accountDetails = accountDetailsRespository.findByUsernameOrEmail(username)
            .orElseThrow(
                    () -> new UsernameNotFoundException("User not found with username: " + username)
            );
        return authenticationDetailsRepository.findById(accountDetails.getUserId())
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
            );
    }

    @Override
    public User saveUserWithDetials(SignUpUser signUpUser) {

        if (signUpUser.isPasswordMismatch()) {
            throw new PasswordMismatchException("passwords don't match");
        }
        if(accountDetailsRespository.existsByEmail(signUpUser.getEmail())) {
            throw new UsernameNotFoundException("account with this email already exists");
        }

        if (accountDetailsRespository.existsByUsername(signUpUser.getUsername())) {
            throw new UsernameNotFoundException("username is not available");
        }
        var authenticationDetails = UserAuthenticationDetails.builder()
            .passwordEncoder(passwordEncoder::encode)
            .password(signUpUser.getPassword())
            .roles("USER")
            .build();

        authenticationDetails = authenticationDetailsRepository.save(authenticationDetails);

        var accountDetails = UserAccountDetails.builder()
            .userId(authenticationDetails.getUserId())
            .username(signUpUser.getUsername())
            .email(signUpUser.getEmail())
            .build();

        accountDetails = accountDetailsRespository.save(accountDetails);



        return new User(accountDetails, authenticationDetails);
    }
}
