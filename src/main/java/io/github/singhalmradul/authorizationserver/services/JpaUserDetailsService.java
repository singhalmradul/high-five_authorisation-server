package io.github.singhalmradul.authorizationserver.services;

import io.github.singhalmradul.authorizationserver.model.SignUpUser;
import io.github.singhalmradul.authorizationserver.model.User;
import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;

public interface JpaUserDetailsService {

    UserAuthenticationDetails getByUsernameOrEmail(String username);

    User saveUserWithDetials(SignUpUser signUpUser);

}