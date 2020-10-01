package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class UpdateIdMismatchException extends SuperException {

    public UpdateIdMismatchException() {
        super();
    }

    public UpdateIdMismatchException(String message) {
        super(message);
    }

    public UpdateIdMismatchException(HttpStatus status) {
        super(status);
    }

    public UpdateIdMismatchException(HttpStatus status, String message) {
        super(status, message);
    }

    public UpdateIdMismatchException(HttpStatus status, String message, String errorCode, String error) {
        super(status, message, errorCode, error);
    }
}