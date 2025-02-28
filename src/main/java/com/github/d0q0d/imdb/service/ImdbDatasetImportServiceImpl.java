package com.github.d0q0d.imdb.service;

import com.github.d0q0d.imdb.model.Person;
import com.github.d0q0d.imdb.model.Rating;
import com.github.d0q0d.imdb.model.Title;
import com.github.d0q0d.imdb.service.exception.DatasetImportInProgressException;
import com.github.d0q0d.imdb.service.exception.NotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import com.github.d0q0d.imdb.model.DatasetStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImdbDatasetImportServiceImpl implements ImdbDatasetImportService {

  private final ImdbDataHolder dataHolder;

  public void importDataset(String filePath) {
    checkPreConditions(filePath);
    try {
      dataHolder.setDatasetStatusEnum(DatasetStatusEnum.PROCESSING);
      var executorService = Executors.newFixedThreadPool(4);
      var personsFuture = CompletableFuture.runAsync(() -> importPersons(filePath), executorService);
      var titlesFuture = CompletableFuture.runAsync(() -> importTitles(filePath), executorService);
      var crewsFuture = CompletableFuture.runAsync(() -> importCrews(filePath), executorService);
      var ratingsFuture = CompletableFuture.runAsync(() -> importRatings(filePath), executorService);
      CompletableFuture.allOf(personsFuture, titlesFuture, crewsFuture, ratingsFuture).join();
      executorService.shutdown();
      dataHolder.setDatasetStatusEnum(DatasetStatusEnum.IMPORTED);
    } catch (Exception e) {
      System.err.println("Error during data import: " + e.getMessage());
      dataHolder.setDatasetStatusEnum(DatasetStatusEnum.NOT_IMPORTED);
    }
  }

  private void importPersons(String filePath) {
    log.info("Starting the import of persons");

    try (var fileReader = new FileReader(filePath + "name.basics.tsv");
        var parser = CSVFormat.TDF.withFirstRecordAsHeader().parse(fileReader)) {
      for (CSVRecord record : parser) {
        var nconst = record.get("nconst");
        var primaryName = record.get("primaryName");
        var birthYear = parseIntOrDefault(record.get("birthYear"));
        var deathYear = parseIntOrDefault(record.get("deathYear"));
        var primaryProfession = splitToList(record.get("primaryProfession"));
        var knownForTitles = splitToList(record.get("knownForTitles"));
        var person = Person.builder()
                .nconst(nconst)
                .primaryName(primaryName)
                .birthYear(birthYear)
                .deathYear(deathYear)
                .primaryProfession(primaryProfession)
                .knownForTitles(knownForTitles)
                .build();
        dataHolder.getPersons().put(nconst, person);
        if (primaryProfession.contains("actor") || primaryProfession.contains("actress")) {
          for (var title : knownForTitles) {
            dataHolder.getActorTitles().computeIfAbsent(nconst, k -> new ArrayList<>()).add(title);
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading file");
    } catch (Exception e) {
      System.err.println("Skipping bad record");
    }
    log.info("Ending the import of persons");
  }

  private void importTitles(String filePath) {
    log.info("Starting the import of titles");
    try (var fileReader = new FileReader(filePath + "title.basics.tsv");
        var parser = CSVFormat.TDF.withFirstRecordAsHeader().parse(fileReader)) {
      for (CSVRecord record : parser) {
        var tconst = record.get("tconst");
        var titleType = record.get("titleType");
        var primaryTitle = record.get("primaryTitle");
        var originalTitle = record.get("originalTitle");
        var isAdult = parseIntOrDefault(record.get("isAdult"));
        var startYear = parseIntOrDefault(record.get("startYear"));
        var endYear = parseIntOrDefault(record.get("endYear"));
        var runtimeMinutes = parseIntOrDefault(record.get("runtimeMinutes"));
        var genres = splitToList(record.get("genres"));
        var title = Title.builder().tconst(tconst)
                .titleType(titleType)
                .primaryTitle(primaryTitle)
                .originalTitle(originalTitle)
                .isAdult(isAdult)
                .startYear(startYear)
                .endYear(endYear)
                .runtimeMinutes(runtimeMinutes)
                .genres(genres)
                .build();
        dataHolder.getTitles().put(tconst, title);
      }
    } catch (IOException e) {
      System.err.println("Error reading file");
    } catch (Exception e) {
      System.err.println("Skipping bad record");
    }
    log.info("Ending the import of titles");
  }

  private void importCrews(String filePath) {
    log.info("Starting the import of crews");
    try (var fileReader = new FileReader(filePath + "title.crew.tsv");
        var parser = CSVFormat.TDF.withFirstRecordAsHeader().parse(fileReader)) {
      for (CSVRecord record : parser) {
        var tconst = record.get("tconst");
        var directors = splitToList(record.get("directors"));
        var writers = splitToList(record.get("writers"));
        for (var director : directors) {
          if (writers.contains(director)) {
            var person = dataHolder.getPersons().get(director);
            if (person != null) {
              dataHolder
                  .getDirectorWriterTitles()
                  .computeIfAbsent(director, k -> new ArrayList<>())
                  .add(tconst);
            }
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading file");
    } catch (Exception e) {
      System.err.println("Skipping bad record");
    }
    log.info("Ending the import of crews");
  }

  private void importRatings(String filePath) {
    log.info("Starting the import of ratings");
    try (var fileReader = new FileReader(filePath + "title.ratings.tsv");
        var parser = CSVFormat.TDF.withFirstRecordAsHeader().parse(fileReader)) {
      for (CSVRecord record : parser) {
        var tconst = record.get("tconst");
        var averageRating = parseDoubleOrDefault(record.get("averageRating"));
        var numVotes = parseIntOrDefault(record.get("numVotes"));
        var rating = new Rating(tconst, averageRating, numVotes);
        dataHolder.getTitleRatings().put(tconst, rating);
      }
    } catch (IOException e) {
      System.err.println("Error reading file");
    } catch (Exception e) {
      System.err.println("Skipping bad record");
    }
    log.info("Ending the import of ratings");
  }

  private int parseIntOrDefault(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return -1;
    }
  }
  private double parseDoubleOrDefault(String value) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return -1L;
    }
  }

  private List<String> splitToList(String input) {
    if (input == null || input.trim().isEmpty()) {
      return Collections.emptyList();
    }
    return Arrays.asList(input.split(","));
  }

  private void checkPreConditions(String filePath) {
    checkIfDataImportAlreadyInProgress();
    checkIfDatasetIsExist(filePath);
  }

  private void checkIfDatasetIsExist(String filePath) {
    if (!Stream.of("name.basics.tsv", "title.basics.tsv", "title.crew.tsv", "title.ratings.tsv")
        .allMatch(fileName -> new File(filePath, fileName).exists())) {
      throw new NotFoundException("Dataset Files Not Exist in path");
    }
  }

  private void checkIfDataImportAlreadyInProgress() {
    if (dataHolder.getDatasetStatusEnum().equals(DatasetStatusEnum.PROCESSING)) {
      throw new DatasetImportInProgressException("Import is already in progress. Please wait.");
    }
  }
}
