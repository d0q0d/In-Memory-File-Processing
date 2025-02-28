package com.github.d0q0d.imdb.api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DatasetInputModel {
  @NotBlank
  private String filePath;
}
