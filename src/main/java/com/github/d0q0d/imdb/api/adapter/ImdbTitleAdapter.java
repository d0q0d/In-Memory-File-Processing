package com.github.d0q0d.imdb.api.adapter;

import com.github.d0q0d.imdb.api.adapter.mapper.ImdbTitleAdapterMapper;
import com.github.d0q0d.imdb.api.controller.dto.TitleOutputModel;
import com.github.d0q0d.imdb.service.ImdbTitleService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImdbTitleAdapter {

  private final ImdbTitleService service;
  private final ImdbTitleAdapterMapper mapper;

  public List<TitleOutputModel> getTitlesWithSameDirectorWriterAlive() {
    return service.getTitlesWithSameDirectorWriterAlive().stream()
        .map(mapper::getTitleOutputModel)
        .collect(Collectors.toList());
  }

  public List<TitleOutputModel> getTitlesWithTwoActorsPlayedAt(
      String firstActor, String secondActor) {
    return service.getTitlesWithTwoActorsPlayedAt(firstActor, secondActor).stream()
        .map(mapper::getTitleOutputModel)
        .collect(Collectors.toList());
  }

  public Map<Integer, TitleOutputModel> getBestTitlesByGenreAndYear(String genre) {
    return mapper.getTitleOutputModelByGenreMap(service.getBestTitlesByGenreAndYear(genre));
  }
}
