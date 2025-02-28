package com.github.d0q0d.imdb.service;

import static com.github.d0q0d.imdb.model.DatasetStatusEnum.IMPORTED;

import com.github.d0q0d.imdb.model.Title;
import com.github.d0q0d.imdb.service.exception.DatasetNotReadyException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImdbTitleServiceImpl implements ImdbTitleService {

  private final ImdbDataHolder imdbDataHolder;

  @Override
  public List<Title> getTitlesWithSameDirectorWriterAlive() {
    checkDatesetIsReady();
    var result = new ArrayList<Title>();
    for (var entry : imdbDataHolder.getDirectorWriterTitles().entrySet()) {
      var nconst = entry.getKey();
      var person = imdbDataHolder.getPersons().get(nconst);
      if (person != null && person.getDeathYear() == -1) {
        for (var tconst : entry.getValue()) {
          var title = imdbDataHolder.getTitles().get(tconst);
          if (title != null) {
            result.add(title);
          }
        }
      }
    }
    return result;
  }

  @Override
  public List<Title> getTitlesWithTwoActorsPlayedAt(String firstActor, String secondActor) {
    checkDatesetIsReady();
    var firstActorTitleList = imdbDataHolder.getActorTitles().getOrDefault(firstActor, Collections.emptyList());
    var secondActorTitleList = imdbDataHolder.getActorTitles().getOrDefault(secondActor, Collections.emptyList());
    var commonTitleSet = new HashSet<>(firstActorTitleList);
    commonTitleSet.retainAll(secondActorTitleList);

    var titleList = new ArrayList<Title>();
    for (var tconst : commonTitleSet) {
      var title = imdbDataHolder.getTitles().get(tconst);
      if (title != null) {
        titleList.add(title);
      }
    }
    return titleList;
  }

  public Map<Integer, Title> getBestTitlesByGenreAndYear(String genre) {
    var resultMap = new HashMap<Integer, Title>();
    for (var entry : imdbDataHolder.getTitles().entrySet()) {
      var title = entry.getValue();
      if (title.getGenres().contains(genre)) {
        var titleRate = imdbDataHolder.getTitleRatings().getOrDefault(title.getTconst(), null);
        if (titleRate != null) {
          var year = title.getStartYear();
          var currentBest = resultMap.get(year);
          if (currentBest == null
              || titleRate.getAverageRating()
                  > imdbDataHolder
                      .getTitleRatings()
                      .get(currentBest.getTconst())
                      .getAverageRating()) {
            resultMap.put(year, title);
          }
        }
      }
    }
    return resultMap;
  }

  public void checkDatesetIsReady() {
    if (!imdbDataHolder.getDatasetStatusEnum().equals(IMPORTED)) {
      throw new DatasetNotReadyException("Dataset not ready yet");
    }
  }
}
