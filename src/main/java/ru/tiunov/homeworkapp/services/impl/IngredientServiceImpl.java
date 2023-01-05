package ru.tiunov.homeworkapp.services.impl;

import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.exception.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.services.IngredientFileService;
import ru.tiunov.homeworkapp.services.IngredientService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
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
            ingredientFileService.initIngredientService(this);
            initializeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveIngredientMap() throws IOException {
        ingredientFileService.writeMapIngredient(ingredientDtoMap);
    }

    @Override
    public List<IngredientDto> getIngredients() {
        return new ArrayList<>(ingredientDtoMap.values());
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
        saveIngredientMap();
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

        ingredientDtoMap.put(id, ingredientDto);
        saveIngredientMap();
        return ingredientDto;
    }

    @Override
    public void deleteIngredient(int id) throws NotFoundElementException, IOException {
        if (ingredientDtoMap.containsKey(id)) {
            ingredientDtoMap.remove(id);
            saveIngredientMap();
        }
        throw new NotFoundElementException("Not found Ingredient");
    }

    @Override
    public void initializeData() throws IOException {
        ingredientDtoMap = ingredientFileService.readIngredientMap();
    }
}
