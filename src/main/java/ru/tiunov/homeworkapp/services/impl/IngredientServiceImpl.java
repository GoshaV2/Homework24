package ru.tiunov.homeworkapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.services.IngredientFileService;
import ru.tiunov.homeworkapp.services.IngredientService;
import ru.tiunov.homeworkapp.services.RecipeService;
import ru.tiunov.homeworkapp.util.observer.Observer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService, Observer {
    private Map<Integer, IngredientDto> ingredientDtoMap;

    private static int lastIngredientId = 0;
    private final IngredientFileService ingredientFileService;

    public IngredientServiceImpl(IngredientFileService ingredientFileService) {
        ingredientDtoMap = new TreeMap<>();
        this.ingredientFileService = ingredientFileService;
    }

    @PostConstruct
    public void init() {
        try {
            ingredientDtoMap = ingredientFileService.readIngredientMap();
        } catch (IOException e) {
            ingredientDtoMap = new TreeMap<>();
            lastIngredientId = 0;
            e.printStackTrace();
        }
        ingredientFileService.register(this);
    }

    private void saveIngredientMap() throws IOException {
        ingredientFileService.writeMapIngredient(ingredientDtoMap);
    }

    @Override
    public List<IngredientDto> getIngredients() {
        return ingredientDtoMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public IngredientDto getIngredientById(int id) throws NotFoundElementException {
        if (ingredientDtoMap.containsKey(id)) {
            return ingredientDtoMap.get(id);
        }
        throw new NotFoundElementException("Not found Ingredient");
    }

    @Override
    public IngredientDto createIngredient(Ingredient ingredient) throws IOException {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(++lastIngredientId);
        ingredientDto.setCount(ingredient.getCount());
        ingredientDto.setTitle(ingredient.getTitle());
        ingredientDto.setUnitOfMeasurement(ingredient.getUnitOfMeasurement());
        ingredientDtoMap.put(ingredientDto.getId(), ingredientDto);
        try {
            saveIngredientMap();
        } catch (IOException e) {
            ingredientDtoMap.remove(ingredientDto.getId());
            throw new IOException();
        }
        return ingredientDto;
    }

    @Override
    public IngredientDto updateIngredient(int id, Ingredient ingredient) throws NotFoundElementException, IOException {
        if (!ingredientDtoMap.containsKey(id)) {
            throw new NotFoundElementException("Not found Ingredient");
        }
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(id);
        ingredientDto.setTitle(ingredient.getTitle());
        ingredientDto.setCount(ingredient.getCount());
        ingredientDto.setUnitOfMeasurement(ingredient.getUnitOfMeasurement());

        IngredientDto ingredientDtoOld = ingredientDtoMap.get(id);
        ingredientDtoMap.put(id, ingredientDto);
        try {
            saveIngredientMap();
        } catch (IOException e) {
            ingredientDtoMap.put(id, ingredientDtoOld);
            throw new IOException();
        }
        return ingredientDto;
    }

    @Override
    public void deleteIngredient(int id) throws NotFoundElementException, IOException {
        if (ingredientDtoMap.containsKey(id)) {
            ingredientDtoMap.remove(id);
            IngredientDto ingredientDtoOld = ingredientDtoMap.get(id);
            try {
                saveIngredientMap();
            } catch (IOException e) {
                ingredientDtoMap.put(id, ingredientDtoOld);
                throw new IOException();
            }
        }
        throw new NotFoundElementException("Not found Ingredient");
    }

    @Override
    public void update() {
        try {
            ingredientDtoMap = ingredientFileService.readIngredientMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
