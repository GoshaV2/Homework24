package ru.tiunov.homeworkapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.services.RecipeFileService;
import ru.tiunov.homeworkapp.util.observer.Observer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class RecipeFileServiceImpl extends FileAbstractService implements RecipeFileService {
    @Value("${data.recipe.file.paths}")
    private String dir;
    @Value("${data.recipe.file.name}")
    private String name;

    private Set<Observer> observers;

    public RecipeFileServiceImpl() {
        observers = new HashSet<>();
    }

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

    @Override
    public InputStreamResource getInputStreamOfRecipeData() throws FileNotFoundException {
        return getInputStreamOfFile(dir + name);
    }

    @Override
    public void importRecipeData(InputStream inputStream) throws JsonProcessingException {
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
