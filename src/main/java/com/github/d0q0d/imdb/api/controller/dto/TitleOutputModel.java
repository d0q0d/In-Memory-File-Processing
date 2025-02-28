package com.github.d0q0d.imdb.api.controller.dto;

import java.util.List;
import lombok.Data;

@Data
public class TitleOutputModel {
  private String tconst;
  private String titleType;
  private String primaryTitle;
  private String originalTitle;
  private Integer isAdult;
  private Integer startYear;
  private Integer endYear;
  private Integer runtimeMinutes;
  private List<String> genres;
}
