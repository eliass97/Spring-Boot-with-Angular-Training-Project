package com.example.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class ExceptionForm {

  private Timestamp timestamp;

  @JsonIgnore
  private HttpStatus status;

  private String errorCode;
  private String error;
  private String exception;
  private String message;
  private String path;

  public ExceptionForm(Timestamp timestamp, HttpStatus status, String errorCode, String error, String exception, String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.errorCode = errorCode;
    this.error = error;
    this.exception = exception;
    this.message = message;
    this.path = path;
  }

  @JsonProperty("status")
  public int getStatusCode() {
    return status.value();
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getError() {
    return error;
  }

  public String getException() {
    return exception;
  }

  public String getPath() {
    return path;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
