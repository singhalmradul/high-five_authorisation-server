package io.github.singhalmradul.authorizationserver.services;

import io.github.singhalmradul.authorizationserver.model.SignUpUser;
import io.github.singhalmradul.authorizationserver.model.user.User;

public interface JpaUserDetailsService {

    User loadUserByUsername(String email);

    User saveUserWithDetials(SignUpUser signUpUser);
}