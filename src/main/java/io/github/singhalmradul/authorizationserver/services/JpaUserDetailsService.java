package io.github.singhalmradul.authorizationserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.authorizationserver.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service("jpaUserDetailsService")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsernameOrEmail(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username)
            );
    }
}
