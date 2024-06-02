package io.github.singhalmradul.authorizationserver.exceptions;

public class PasswordMismatchException extends SignUpException {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
