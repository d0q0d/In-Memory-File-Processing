package com.github.d0q0d.imdb.service.exception;

public class NotFoundException extends RuntimeException {

  private String message;

  public NotFoundException() {}

  public NotFoundException(String message) {
    super(message);
    this.message = message;
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
