package com.github.d0q0d.imdb.service;

import com.github.d0q0d.imdb.model.Title;
import java.util.List;
import java.util.Map;

public interface ImdbTitleService {
  List<Title> getTitlesWithSameDirectorWriterAlive();

  List<Title> getTitlesWithTwoActorsPlayedAt(String firstActor, String secondActor);

  Map<Integer, Title> getBestTitlesByGenreAndYear(String genre);
}
