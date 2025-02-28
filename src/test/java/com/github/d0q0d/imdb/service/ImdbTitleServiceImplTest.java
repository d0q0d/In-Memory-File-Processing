package com.github.d0q0d.imdb.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.d0q0d.imdb.model.DatasetStatusEnum;
import com.github.d0q0d.imdb.model.Person;
import com.github.d0q0d.imdb.model.Rating;
import com.github.d0q0d.imdb.model.Title;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImdbTitleServiceImplTest {

  @Mock private ImdbDataHolder imdbDataHolder;

  @InjectMocks private ImdbTitleServiceImpl imdbTitleService;

  @Test
  void testGetTitlesWithSameDirectorWriterAlive() {
    when(imdbDataHolder.getDatasetStatusEnum()).thenReturn(DatasetStatusEnum.IMPORTED);
    var directorWriterTitlesMap = new HashMap<String, List<String>>();
    directorWriterTitlesMap.put("nm0000001", List.of("tt0000001", "tt0000002"));
    Map<String, Person> persons = new HashMap<>();
    persons.put("nm0000001", new Person("nm0000001", "Brad Pitt", 1970, -1, List.of("director"), List.of("tt0000001", "tt0000002")));
    Map<String, Title> titles = new HashMap<>();
    titles.put("tt0000001", new Title("tt0000001", "movie", "Title 1", "Title 1", 0, 2020, -1, 120, List.of("Action")));
    titles.put("tt0000002", new Title("tt0000002", "movie", "Title 2", "Title 2", 0, 2021, -1, 130, List.of("Drama")));
    when(imdbDataHolder.getDirectorWriterTitles()).thenReturn(directorWriterTitlesMap);
    when(imdbDataHolder.getPersons()).thenReturn(persons);
    when(imdbDataHolder.getTitles()).thenReturn(titles);
    var titleList = imdbTitleService.getTitlesWithSameDirectorWriterAlive();
    assertEquals(2, titleList.size());
    assertTrue(titleList.stream().anyMatch(title -> title.getTconst().equals("tt0000001")));
    assertTrue(titleList.stream().anyMatch(title -> title.getTconst().equals("tt0000002")));
  }

  @Test
  void testGetTitlesWithTwoActorsPlayedAt() {
    when(imdbDataHolder.getDatasetStatusEnum()).thenReturn(DatasetStatusEnum.IMPORTED);
    var actorTitleMap = new HashMap<String, List<String>>();
    actorTitleMap.put("nm0000001", List.of("tt0000001", "tt0000002"));
    actorTitleMap.put("nm0000002", List.of("tt0000001", "tt0000003"));
    var titleMap = new HashMap<String, Title>();
    titleMap.put("tt0000001", new Title("tt0000001", "movie", "Title 1", "Title 1", 0, 2020, -1, 120, List.of("Action")));
    titleMap.put("tt0000002", new Title("tt0000002", "movie", "Title 2", "Title 2", 0, 2021, -1, 130, List.of("Drama")));
    titleMap.put("tt0000003", new Title("tt0000003", "movie", "Title 3", "Title 3", 0, 2022, -1, 140, List.of("Comedy")));
    when(imdbDataHolder.getActorTitles()).thenReturn(actorTitleMap);
    when(imdbDataHolder.getTitles()).thenReturn(titleMap);
    var titleList = imdbTitleService.getTitlesWithTwoActorsPlayedAt("nm0000001", "nm0000002");
    assertEquals(1, titleList.size());
    Assertions.assertEquals("tt0000001", titleList.get(0).getTconst());
  }

  @Test
  void testGetBestTitlesByGenreAndYear() {
    var titleMap = new HashMap<String, Title>();
    titleMap.put("tt0000001", new Title("tt0000001", "movie", "Title 1", "Title 1", 0, 2020, -1, 120, List.of("Action")));
    titleMap.put("tt0000002", new Title("tt0000002", "movie", "Title 2", "Title 2", 0, 2020, -1, 130, List.of("Action")));
    titleMap.put("tt0000003", new Title("tt0000003", "movie", "Title 3", "Title 3", 0, 2021, -1, 140, List.of("Action")));
    var titleRatingMap = new HashMap<String, Rating>();
    titleRatingMap.put("tt0000001", new Rating("tt0000001", 8.5, 1000));
    titleRatingMap.put("tt0000002", new Rating("tt0000002", 9.0, 1500));
    titleRatingMap.put("tt0000003", new Rating("tt0000003", 7.5, 800));
    when(imdbDataHolder.getTitles()).thenReturn(titleMap);
    when(imdbDataHolder.getTitleRatings()).thenReturn(titleRatingMap);
    var yearTitleMap = imdbTitleService.getBestTitlesByGenreAndYear("Action");
    assertEquals(2, yearTitleMap.size());
    Assertions.assertEquals("tt0000002", yearTitleMap.get(2020).getTconst());
    Assertions.assertEquals("tt0000003", yearTitleMap.get(2021).getTconst());
  }

}
