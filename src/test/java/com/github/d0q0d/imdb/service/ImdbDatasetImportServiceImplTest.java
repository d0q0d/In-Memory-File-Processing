package com.github.d0q0d.imdb.service;

import static org.mockito.Mockito.*;

import com.github.d0q0d.imdb.model.DatasetStatusEnum;
import com.github.d0q0d.imdb.service.exception.DatasetImportInProgressException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImdbDatasetImportServiceImplTest {

  @Mock private ImdbDataHolder dataHolder;

  @InjectMocks private ImdbDatasetImportServiceImpl imdbDatasetImportService;

  @BeforeEach
  void setUp() {
    when(dataHolder.getDatasetStatusEnum()).thenReturn(DatasetStatusEnum.NOT_IMPORTED);
  }

  private final String datasetPath =
      "/Users/darkblue/Documents/Projects/imdb/imdb/src/test/resources/";

  @Test
  void shouldImportDataIfImportNotInProgress() {
    when(dataHolder.getDatasetStatusEnum()).thenReturn(DatasetStatusEnum.NOT_IMPORTED);
    imdbDatasetImportService.importDataset(datasetPath);
    verify(dataHolder, times(1)).setDatasetStatusEnum(DatasetStatusEnum.PROCESSING);
    verify(dataHolder, times(1)).setDatasetStatusEnum(DatasetStatusEnum.IMPORTED);
  }

  @Test
  void shouldThrowExceptionIfImportInProgress() {
    when(dataHolder.getDatasetStatusEnum()).thenReturn(DatasetStatusEnum.NOT_IMPORTED);
    Mockito.doThrow(new DatasetImportInProgressException("Test exception"))
        .when(dataHolder)
        .setDatasetStatusEnum(DatasetStatusEnum.PROCESSING);
    imdbDatasetImportService.importDataset(datasetPath);
    verify(dataHolder, times(1)).setDatasetStatusEnum(DatasetStatusEnum.NOT_IMPORTED);
  }
}
