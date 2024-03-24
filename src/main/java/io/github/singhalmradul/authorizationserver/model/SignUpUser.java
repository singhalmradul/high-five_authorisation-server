package io.github.singhalmradul.authorizationserver.model;

import lombok.Data;

@Data
public class SignUpUser {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public boolean isPasswordMismatch() {
        return !password.equals(confirmPassword);
    }
}
