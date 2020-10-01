package com.example.demo.exceptions;

public class UpdateIdMismatchException extends Exception {

    public UpdateIdMismatchException() {
        super();
    }

    public UpdateIdMismatchException(String message) {
        super(message);
    }

    public UpdateIdMismatchException(Throwable cause) {
        super(cause);
    }

    public UpdateIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
