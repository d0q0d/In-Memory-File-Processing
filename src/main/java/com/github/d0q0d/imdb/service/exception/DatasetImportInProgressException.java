package com.github.d0q0d.imdb.service.exception;

public class DatasetImportInProgressException extends RuntimeException {

  private String message;

  public DatasetImportInProgressException() {}

  public DatasetImportInProgressException(String message) {
    super(message);
    this.message = message;
  }

  public DatasetImportInProgressException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
