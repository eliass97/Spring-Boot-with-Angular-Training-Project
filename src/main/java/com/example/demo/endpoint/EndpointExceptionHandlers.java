package com.example.demo.endpoint;

import com.example.demo.exceptions.CountryNotFoundException;
import com.example.demo.exceptions.UpdateIdMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EndpointExceptionHandlers {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(CountryNotFoundException.class)
    public void handleNotFoundException() {

    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Mismatch between parameter and body id")
    @ExceptionHandler(UpdateIdMismatchException.class)
    public void handleIdMismatchException() {

    }
}
