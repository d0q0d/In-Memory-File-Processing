package com.github.d0q0d.imdb.service;

import static com.github.d0q0d.imdb.model.DatasetStatusEnum.NOT_IMPORTED;

import com.github.d0q0d.imdb.model.DatasetStatusEnum;
import com.github.d0q0d.imdb.model.Person;
import com.github.d0q0d.imdb.model.Rating;
import com.github.d0q0d.imdb.model.Title;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ImdbDataHolder {
  private final Map<String, Person> persons = new HashMap<>();
  private final Map<String, Title> titles = new HashMap<>();
  private final Map<String, List<String>> directorWriterTitles = new HashMap<>();
  private final Map<String, List<String>> actorTitles = new HashMap<>();
  private final Map<String, Rating> titleRatings = new HashMap<>();
  private DatasetStatusEnum datasetStatusEnum = NOT_IMPORTED;

  public void setDatasetStatusEnum(DatasetStatusEnum datasetStatusEnum) {
    this.datasetStatusEnum = datasetStatusEnum;
  }
}
