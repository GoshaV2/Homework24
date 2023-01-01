package ru.tiunov.homeworkapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.services.IngredientFileService;
import ru.tiunov.homeworkapp.util.observer.Observer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class IngredientFileServiceImpl extends FileAbstractService implements IngredientFileService {
    @Value("${data.ingredient.file.paths}")
    private String dir;
    @Value("${data.ingredient.file.name}")
    private String name;

    private Set<Observer> observers;

    public IngredientFileServiceImpl() {
        observers = new HashSet<>();
    }

    @Override
    public Map<Integer, IngredientDto> readIngredientMap() {
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
    public void writeMapIngredient(Map<Integer, IngredientDto> ingredientMap) {
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
    public void importIngredientData(InputStream inputStream) throws JsonProcessingException {
        importFileFromInputStream(inputStream, dir + name);
        notifyObservers();
    }

    @Override
    public void register(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
