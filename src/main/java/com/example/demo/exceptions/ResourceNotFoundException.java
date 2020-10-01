package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends SuperException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(HttpStatus status) {
        super(status);
    }

    public ResourceNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }

    public ResourceNotFoundException(HttpStatus status, String message, String errorCode, String error) {
        super(status, message, errorCode, error);
    }
}