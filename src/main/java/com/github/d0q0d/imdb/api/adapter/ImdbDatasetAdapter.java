package com.github.d0q0d.imdb.api.adapter;

import com.github.d0q0d.imdb.api.controller.dto.DatasetInputModel;
import com.github.d0q0d.imdb.api.controller.dto.DatasetOutputModel;
import com.github.d0q0d.imdb.service.ImdbDatasetImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImdbDatasetAdapter {

  private final ImdbDatasetImportService service;

  public DatasetOutputModel importDataset(DatasetInputModel datasetInputModel) {
    service.importDataset(datasetInputModel.getFilePath());
    return new DatasetOutputModel("Dataset imported successfully");
  }
}
