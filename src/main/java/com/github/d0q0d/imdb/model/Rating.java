package com.github.d0q0d.imdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {
  private String tconst;
  private Double averageRating;
  private Integer numVotes;
}
