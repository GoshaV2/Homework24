package ru.tiunov.homeworkapp.services;

import org.springframework.core.io.InputStreamResource;
import ru.tiunov.homeworkapp.dto.IngredientDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface IngredientFileService {
    Map<Integer, IngredientDto> readIngredientMap() throws IOException;

    void writeMapIngredient(Map<Integer, IngredientDto> ingredientMap) throws IOException;

    InputStreamResource getInputStreamOfIngredientData() throws FileNotFoundException;

    void importIngredientData(InputStream inputStream) throws IOException;

    void initIngredientService(IngredientService ingredientService);
}
