package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends DemoException {

  public ConflictException() {
    super(HttpStatus.CONFLICT, "CONFLICT", "Conflict");
  }

  public ConflictException(String message) {
    super(HttpStatus.CONFLICT, message, "CONFLICT", "Conflict");
  }
}
