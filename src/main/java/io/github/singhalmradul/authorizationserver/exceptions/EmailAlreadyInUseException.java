package io.github.singhalmradul.authorizationserver.exceptions;

public class EmailAlreadyInUseException extends SignUpException {

    public EmailAlreadyInUseException(String message) {
        super(message);
    }

}
