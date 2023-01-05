package ru.tiunov.homeworkapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.services.RecipeFileService;
import ru.tiunov.homeworkapp.services.RecipeService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeFileServiceImpl extends FileAbstractService implements RecipeFileService {
    @Value("${data.recipe.file.paths}")
    private String dir;
    @Value("${data.recipe.file.name}")
    private String name;

    private RecipeService recipeService;

    @Override
    public void initRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Map<Integer, RecipeDto> readRecipeMap() throws IOException {
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
    public void writeRecipeMap(Map<Integer, RecipeDto> recipeMap) throws IOException {
        try {
            write(new ObjectMapper().writeValueAsString(recipeMap), dir + name);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStreamResource getInputStreamOfRecipeData() throws FileNotFoundException {
        return getInputStreamOfFile(dir + name);
    }

    @Override
    public void importRecipeData(InputStream inputStream) throws IOException {
        importFileFromInputStream(inputStream, dir + name);
        recipeService.initializeData();
    }
}
