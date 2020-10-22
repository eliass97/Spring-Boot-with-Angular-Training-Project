package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DemoException {

  public BadRequestException() {
    super(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Bad Request");
  }

  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message, "BAD_REQUEST", "Bad Request");
  }
}
