package ru.tiunov.homeworkapp.services;

import org.springframework.core.io.InputStreamResource;
import ru.tiunov.homeworkapp.dto.RecipeDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface RecipeFileService {
    Map<Integer, RecipeDto> readRecipeMap() throws IOException;

    void writeRecipeMap(Map<Integer, RecipeDto> recipeMap) throws IOException;

    InputStreamResource getInputStreamOfRecipeData() throws FileNotFoundException;

    void importRecipeData(InputStream inputStream) throws IOException;

    void initRecipeService(RecipeService recipeService);
}
