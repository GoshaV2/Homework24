package ru.tiunov.homeworkapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.services.RecipeFileService;

import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeFileServiceImpl extends FileAbstractService implements RecipeFileService {
    @Value("${data.recipe.file.paths}")
    private String dir;
    @Value("${data.recipe.file.name}")
    private String name;

    @Override
    public Map<Integer, RecipeDto> readRecipeMap() {
        Map<Integer, RecipeDto> recipeDtoMap = new TreeMap<>();
        try {
            String json = read(dir + name);
            if (!(json == null || json.isEmpty())) {
                recipeDtoMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, RecipeDto>>() {
                });
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return recipeDtoMap;
    }

    @Override
    public void writeRecipeMap(Map<Integer, RecipeDto> recipeMap) {
        try {
            write(new ObjectMapper().writeValueAsString(recipeMap), dir + name);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
