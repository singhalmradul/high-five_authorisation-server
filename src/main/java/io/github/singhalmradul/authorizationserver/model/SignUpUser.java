package io.github.singhalmradul.authorizationserver.model;

import lombok.Data;

@Data
public class SignUpUser {

    private String email;
    private String password;
    private String confirmPassword;

    public boolean isPasswordsMismatch() {
        return !password.equals(confirmPassword);
    }
}
