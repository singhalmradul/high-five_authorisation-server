package io.github.singhalmradul.authorizationserver.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException(String message) {
        super(message);
    }

}
