package com.example.demo.exceptions;

public class CountryNotFoundException extends Exception {

    public CountryNotFoundException() {
        super();
    }

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(Throwable cause) {
        super(cause);
    }

    public CountryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
