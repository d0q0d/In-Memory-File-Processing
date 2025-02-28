package com.github.d0q0d.imdb.api.adapter.mapper;

import com.github.d0q0d.imdb.api.controller.dto.TitleOutputModel;
import com.github.d0q0d.imdb.model.Title;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T14:15:57+0330",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class ImdbTitleAdapterMapperImpl implements ImdbTitleAdapterMapper {

    @Override
    public TitleOutputModel getTitleOutputModel(Title title) {
        if ( title == null ) {
            return null;
        }

        TitleOutputModel titleOutputModel = new TitleOutputModel();

        titleOutputModel.setTconst( title.getTconst() );
        titleOutputModel.setTitleType( title.getTitleType() );
        titleOutputModel.setPrimaryTitle( title.getPrimaryTitle() );
        titleOutputModel.setOriginalTitle( title.getOriginalTitle() );
        titleOutputModel.setIsAdult( title.getIsAdult() );
        titleOutputModel.setStartYear( title.getStartYear() );
        titleOutputModel.setEndYear( title.getEndYear() );
        titleOutputModel.setRuntimeMinutes( title.getRuntimeMinutes() );
        List<String> list = title.getGenres();
        if ( list != null ) {
            titleOutputModel.setGenres( new ArrayList<String>( list ) );
        }

        return titleOutputModel;
    }

    @Override
    public Map<Integer, TitleOutputModel> getTitleOutputModelByGenreMap(Map<Integer, Title> bestTitlesByGenreAndYear) {
        if ( bestTitlesByGenreAndYear == null ) {
            return null;
        }

        Map<Integer, TitleOutputModel> map = new LinkedHashMap<Integer, TitleOutputModel>( Math.max( (int) ( bestTitlesByGenreAndYear.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Integer, Title> entry : bestTitlesByGenreAndYear.entrySet() ) {
            Integer key = entry.getKey();
            TitleOutputModel value = getTitleOutputModel( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }
}
