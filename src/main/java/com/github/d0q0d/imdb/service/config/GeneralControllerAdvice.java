package com.github.d0q0d.imdb.service.config;

import com.github.d0q0d.imdb.service.exception.DatasetImportInProgressException;
import com.github.d0q0d.imdb.service.exception.DatasetNotReadyException;
import com.github.d0q0d.imdb.service.exception.NotFoundException;
import com.github.d0q0d.imdb.service.exception.model.ErrorResponse;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GeneralControllerAdvice {

  @ExceptionHandler(DatasetImportInProgressException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public @ResponseBody ErrorResponse datasetImportInProgressExceptionHandler(
      DatasetImportInProgressException e) {
    log.warn("datasetImportInProgressExceptionHandler", e);
    return ErrorResponse.builder()
        .message(e.getMessage())
        .fingerPrint(UUID.randomUUID().toString())
        .build();
  }

  @ExceptionHandler(DatasetNotReadyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse datasetNotReadyExceptionHandler(DatasetNotReadyException e) {
    log.warn("datasetNotReadyExceptionHandler", e);
    return ErrorResponse.builder()
        .message(e.getMessage())
        .fingerPrint(UUID.randomUUID().toString())
        .build();
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse notFoundExceptionHandler(NotFoundException e) {
    log.warn("notFoundExceptionHandler", e);
    return ErrorResponse.builder()
        .message(e.getMessage())
        .fingerPrint(UUID.randomUUID().toString())
        .build();
  }
}
