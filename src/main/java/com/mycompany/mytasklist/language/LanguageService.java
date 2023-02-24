package com.mycompany.mytasklist.language;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 *
 * @author marcin
 */
public class LanguageService {
    public static final LanguageDTO defaultLanguageDTO = new LanguageDTO();
    private LanguageRepository repository;
    //Obiekt, w ktorym zachodzi mapowanie danych na jsona
    private ObjectMapper mapper;
    
    private final Logger logger = LoggerFactory.getLogger(LanguageService.class);
    
    public LanguageService() {
        this(new LanguageRepository(), new ObjectMapper());
    }
    
    public LanguageService(LanguageRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    public String getLanguages() throws JsonProcessingException {
        String data;
        //przy pomocy metody z mappera wysylamy dane z bazy
        try {
            List<LanguageDTO> list = cast(repository.findAll());
            data = mapper.writeValueAsString(list);
        }
        catch (JsonProcessingException e){
            logger.warn("Problem during processing languages data from database");
            data = mapper.writeValueAsString(defaultLanguageDTO);
        }
        return data;
    }
    
    //funkcja cast oparta na typach generycznych
    //rzutuje obiekty listy klasy o typie ? - wildcard
    public static <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }
}
