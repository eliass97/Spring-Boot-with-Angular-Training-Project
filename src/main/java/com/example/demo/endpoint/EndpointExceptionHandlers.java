package com.example.demo.endpoint;

import com.example.demo.exceptions.ExceptionForm;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.SuperException;
import com.example.demo.exceptions.UpdateIdMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestControllerAdvice
public class EndpointExceptionHandlers {

    @ExceptionHandler({ResourceNotFoundException.class, UpdateIdMismatchException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionForm CommonExceptionHandler(SuperException exception, HttpServletRequest request) {
        return new ExceptionForm(new Timestamp(System.currentTimeMillis()),
                exception.getStatus(),
                exception.getErrorCode(),
                exception.getError(),
                exception.getClass().getCanonicalName(),
                exception.getMessage(),
                request.getServletPath());
    }
}