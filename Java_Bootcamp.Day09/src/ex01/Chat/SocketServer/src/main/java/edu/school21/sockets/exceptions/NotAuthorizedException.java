package edu.school21.sockets.exceptions;

public class NotAuthorizedException extends  RuntimeException {
    public NotAuthorizedException(String message) {
        super(message);
    }
}

