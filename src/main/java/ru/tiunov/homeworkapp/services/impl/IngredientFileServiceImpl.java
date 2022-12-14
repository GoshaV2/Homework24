package ru.tiunov.homeworkapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.services.IngredientFileService;
import ru.tiunov.homeworkapp.services.IngredientService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientFileServiceImpl extends FileAbstractService implements IngredientFileService {
    @Value("${data.ingredient.file.paths}")
    private String dir;
    @Value("${data.ingredient.file.name}")
    private String name;

    private IngredientService ingredientService;

    @Override
    public void initIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public Map<Integer, IngredientDto> readIngredientMap() throws IOException {
        Map<Integer, IngredientDto> ingredientMap = new TreeMap<>();
        String json = read(dir + name);
        if (!(json == null || json.isEmpty())) {
            try {
                ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, IngredientDto>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return ingredientMap;
    }

    @Override
    public void writeMapIngredient(Map<Integer, IngredientDto> ingredientMap) throws IOException {
        try {
            write(new ObjectMapper().writeValueAsString(ingredientMap), dir + name);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStreamResource getInputStreamOfIngredientData() throws FileNotFoundException {
        return getInputStreamOfFile(dir + name);
    }

    @Override
    public void importIngredientData(InputStream inputStream) throws IOException {
        importFileFromInputStream(inputStream, dir + name);
        ingredientService.initializeData();
    }
}
