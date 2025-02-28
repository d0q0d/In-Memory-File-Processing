package com.github.d0q0d.imdb.api.controller;

import com.github.d0q0d.imdb.api.adapter.ImdbDatasetAdapter;
import com.github.d0q0d.imdb.api.controller.dto.DatasetInputModel;
import com.github.d0q0d.imdb.api.controller.dto.DatasetOutputModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/imdb/dataset")
@RequiredArgsConstructor
public class ImdbDatasetController {

  private final ImdbDatasetAdapter adapter;

  @PostMapping("/import-dataset")
  @ResponseStatus(HttpStatus.OK)
  public DatasetOutputModel importDataset(@RequestBody @Valid DatasetInputModel datasetInputModel) {
    return adapter.importDataset(datasetInputModel);
  }
}
