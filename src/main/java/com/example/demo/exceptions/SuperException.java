package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class SuperException extends Exception {

    private HttpStatus status;
    private String errorCode;
    private String error;

    public SuperException() {
        super();
    }

    public SuperException(String message) {
        super(message);
    }

    public SuperException(HttpStatus status) {
        super();
        this.status = status;
    }

    public SuperException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public SuperException(HttpStatus status, String message, String errorCode, String error) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.error = error;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }
}