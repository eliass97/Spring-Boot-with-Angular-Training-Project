package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class DemoException extends Exception {

    private HttpStatus status;
    private String errorCode;
    private String error;
    private final Timestamp timestamp;

    public DemoException() {
        super();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public DemoException(String message) {
        super(message);
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public DemoException(HttpStatus status) {
        super();
        timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
    }

    public DemoException(HttpStatus status, String message) {
        super(message);
        timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
    }

    public DemoException(HttpStatus status, String errorCode, String error) {
        super();
        timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
        this.errorCode = errorCode;
        this.error = error;
    }

    public DemoException(HttpStatus status, String message, String errorCode, String error) {
        super(message);
        timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
        this.errorCode = errorCode;
        this.error = error;
    }

    public Timestamp getTimestamp() {
        return timestamp;
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