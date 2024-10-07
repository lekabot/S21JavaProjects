package edu.school21.sockets.exceptions;

public class NoSuchRoomException extends RuntimeException {
    public NoSuchRoomException(String message) {
        super(message);
    }
}