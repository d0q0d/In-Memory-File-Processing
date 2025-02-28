package com.github.d0q0d.imdb.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TitleCrew {
  private String tconst;
  private List<String> directors;
  private List<String> writers;
}
