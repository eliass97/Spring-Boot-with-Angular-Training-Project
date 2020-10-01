package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DemoException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "NOT_FOUND", "Not Found");
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message, "NOT_FOUND", "Not Found");
    }
}