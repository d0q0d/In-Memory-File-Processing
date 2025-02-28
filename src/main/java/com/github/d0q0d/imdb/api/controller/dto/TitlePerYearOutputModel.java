package com.github.d0q0d.imdb.api.controller.dto;

import java.util.Map;
import lombok.Data;

@Data
public class TitlePerYearOutputModel {
  private Map<Integer, TitleOutputModel> yearTitleMap;
}
