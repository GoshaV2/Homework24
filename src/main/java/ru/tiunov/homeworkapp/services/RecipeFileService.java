package ru.tiunov.homeworkapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.InputStreamResource;
import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.util.observer.Subject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public interface RecipeFileService extends Subject {
    Map<Integer, RecipeDto> readRecipeMap();

    void writeRecipeMap(Map<Integer, RecipeDto> recipeMap);

    InputStreamResource getInputStreamOfRecipeData() throws FileNotFoundException;

    void importRecipeData(InputStream inputStream) throws JsonProcessingException;
}
