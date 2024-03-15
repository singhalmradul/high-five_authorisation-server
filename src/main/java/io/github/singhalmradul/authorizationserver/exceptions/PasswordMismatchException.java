package io.github.singhalmradul.authorizationserver.exceptions;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
