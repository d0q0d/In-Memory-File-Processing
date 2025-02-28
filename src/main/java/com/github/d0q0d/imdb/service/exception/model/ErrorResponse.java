package com.github.d0q0d.imdb.service.exception.model;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private Set<ValidationError> errorSet = new HashSet<>();
  private String message;
  private String fingerPrint;
}
