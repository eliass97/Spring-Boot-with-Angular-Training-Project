package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.ExceptionForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class EndpointAdvice {

  @ExceptionHandler(DemoException.class)
  public ResponseEntity<ExceptionForm> CommonExceptionHandler(DemoException exception, HttpServletRequest request) {
    ExceptionForm exceptionform = new ExceptionForm(exception.getTimestamp(),
      exception.getStatus(),
      exception.getErrorCode(),
      exception.getError(),
      exception.getClass().getCanonicalName(),
      exception.getMessage(),
      request.getServletPath());
    return new ResponseEntity<ExceptionForm>(exceptionform, exception.getStatus());
  }
}
