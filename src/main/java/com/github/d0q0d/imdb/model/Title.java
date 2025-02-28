package com.github.d0q0d.imdb.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Title {
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
