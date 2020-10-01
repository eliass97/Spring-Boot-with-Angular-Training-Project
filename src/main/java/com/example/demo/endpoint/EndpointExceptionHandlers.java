package com.example.demo.endpoint;

import com.example.demo.exceptions.ExceptionForm;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UpdateIdMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestControllerAdvice
public class EndpointExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionForm handleNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return new ExceptionForm(new Timestamp(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND",
                "Not Found",
                ResourceNotFoundException.class.getCanonicalName(),
                ex.getMessage(),
                request.getServletPath());
    }

    @ExceptionHandler(UpdateIdMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionForm handleIdMismatchException(UpdateIdMismatchException ex, HttpServletRequest request) {
        return new ExceptionForm(new Timestamp(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST,
                "ID_MISMATCH",
                "Bad Request",
                UpdateIdMismatchException.class.getCanonicalName(),
                ex.getMessage(),
                request.getServletPath());
    }
}