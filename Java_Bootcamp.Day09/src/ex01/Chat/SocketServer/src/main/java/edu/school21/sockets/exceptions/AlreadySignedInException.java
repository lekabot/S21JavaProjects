package edu.school21.sockets.exceptions;

public class AlreadySignedInException extends RuntimeException {
    public AlreadySignedInException(String message) {
        super(message);
    }
}