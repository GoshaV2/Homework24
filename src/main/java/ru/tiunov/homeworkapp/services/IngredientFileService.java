package ru.tiunov.homeworkapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.InputStreamResource;
import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.util.observer.Subject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface IngredientFileService extends Subject {
    Map<Integer, IngredientDto> readIngredientMap() throws IOException;

    void writeMapIngredient(Map<Integer, IngredientDto> ingredientMap) throws IOException;

    InputStreamResource getInputStreamOfIngredientData() throws FileNotFoundException;

    void importIngredientData(InputStream inputStream) throws IOException;
}
