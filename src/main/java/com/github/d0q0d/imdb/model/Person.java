package com.github.d0q0d.imdb.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Person {
  private String nconst;
  private String primaryName;
  private Integer birthYear;
  private Integer deathYear;
  private List<String> primaryProfession;
  private List<String> knownForTitles;
}
