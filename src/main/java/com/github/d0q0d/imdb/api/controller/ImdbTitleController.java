package com.github.d0q0d.imdb.api.controller;

import com.github.d0q0d.imdb.api.adapter.ImdbTitleAdapter;
import com.github.d0q0d.imdb.api.controller.dto.TitleOutputModel;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/imdb/titles")
@RequiredArgsConstructor
public class ImdbTitleController {

  private final ImdbTitleAdapter adapter;

  @GetMapping("/director-writer-alive")
  @ResponseStatus(HttpStatus.OK)
  public List<TitleOutputModel> getTitlesWithSameDirectorWriterAlive() {
    return adapter.getTitlesWithSameDirectorWriterAlive();
  }

  @GetMapping("/two-actors-played")
  @ResponseStatus(HttpStatus.OK)
  public List<TitleOutputModel> getTitlesWithTwoActorsPlayedAt(
      @RequestParam(name = "firstActor") String firstActor,
      @RequestParam(name = "secondActor") String secondActor) {
    return adapter.getTitlesWithTwoActorsPlayedAt(firstActor, secondActor);
  }

  @GetMapping("/best-genre")
  @ResponseStatus(HttpStatus.OK)
  public Map<Integer, TitleOutputModel> getBestTitlesByGenreAndYear(
      @RequestParam(name = "genre") String genre) {
    return adapter.getBestTitlesByGenreAndYear(genre);
  }
}
