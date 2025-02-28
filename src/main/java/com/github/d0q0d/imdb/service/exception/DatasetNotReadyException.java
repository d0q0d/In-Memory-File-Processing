package com.github.d0q0d.imdb.service.exception;

public class DatasetNotReadyException extends RuntimeException {

  private String message;

  public DatasetNotReadyException() {}

  public DatasetNotReadyException(String message) {
    super(message);
    this.message = message;
  }

  public DatasetNotReadyException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
