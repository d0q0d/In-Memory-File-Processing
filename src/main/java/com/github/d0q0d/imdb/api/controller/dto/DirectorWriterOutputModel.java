package com.github.d0q0d.imdb.api.controller.dto;

import java.util.List;
import lombok.Data;

@Data
public class DirectorWriterOutputModel {
  private List<TitleOutputModel> titleList;
}
