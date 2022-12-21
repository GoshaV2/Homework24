package ru.tiunov.homeworkapp.services.impl;

import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.services.IngredientService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<Integer, Ingredient> ingredientMap;

    private static int lastIngredientId = 0;

    public IngredientServiceImpl() {
        ingredientMap = new TreeMap<>();
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredientMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public Ingredient getIngredientById(int id) throws NotFoundElementException {
        if (ingredientMap.containsKey(id)) {
            return ingredientMap.get(id);
        }
        throw new NotFoundElementException("Not found Ingredient");
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        ingredient.setId(++lastIngredientId);
        ingredientMap.put(ingredient.getId(), ingredient);
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(int id, Ingredient ingredient) throws NotFoundElementException {
        if (!ingredientMap.containsKey(id)) {
            throw new NotFoundElementException("Not found Ingredient");
        }
        ingredient.setId(id);
        ingredientMap.put(id, ingredient);
        return ingredient;
    }

    @Override
    public void deleteIngredient(int id) throws NotFoundElementException {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
        }
        throw new NotFoundElementException("Not found Ingredient");
    }
}
