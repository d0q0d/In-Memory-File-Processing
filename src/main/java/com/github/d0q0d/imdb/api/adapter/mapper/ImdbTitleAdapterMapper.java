package com.github.d0q0d.imdb.api.adapter.mapper;

import com.github.d0q0d.imdb.api.controller.dto.TitleOutputModel;
import com.github.d0q0d.imdb.model.Title;
import java.util.Map;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImdbTitleAdapterMapper {
  TitleOutputModel getTitleOutputModel(Title title);

  Map<Integer, TitleOutputModel> getTitleOutputModelByGenreMap(Map<Integer, Title> bestTitlesByGenreAndYear);
}
