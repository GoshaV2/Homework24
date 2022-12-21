package ru.tiunov.homeworkapp.services.impl;

import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.models.Recipe;
import ru.tiunov.homeworkapp.services.IngredientService;
import ru.tiunov.homeworkapp.services.RecipeService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final Map<Integer, Recipe> recipes;
    private final IngredientService ingredientService;
    private static int lastRecipeId = 0;

    public RecipeServiceImpl(IngredientService ingredientService) {
        recipes = new TreeMap<>();
        this.ingredientService = ingredientService;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) throws NotFoundElementException {
        recipe.setId(++lastRecipeId);
        List<Ingredient> ingredients = new ArrayList<>();
        for (int id : recipe.getIngredientIds()) {
            ingredients.add(ingredientService.getIngredientById(id));
        }
        recipe.setIngredients(ingredients);
        recipes.put(recipe.getId(), recipe);
        return recipe;
    }

    @Override
    public Recipe getRecipeById(int id) throws NotFoundElementException {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        }
        throw new NotFoundElementException();
    }

    @Override
    public List<Recipe> getRecipes(int page) {
        List<Recipe> recipeList = recipes.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        if (recipeList.size() < (page - 1) * 10 + 1) {
            return new ArrayList<>();
        }
        if (recipeList.size() < (page - 1) * 10 + 10) {
            return recipeList.subList((page - 1) * 10, recipeList.size());
        }
        return recipeList.subList((page - 1) * 10, (page - 1) * 10 + 10);
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) throws NotFoundElementException {
        if (!recipes.containsKey(id)) {
            throw new NotFoundElementException();
        }
        List<Ingredient> ingredients = new ArrayList<>();
        for (int ingredientId : recipe.getIngredientIds()) {
            ingredients.add(ingredientService.getIngredientById(ingredientId));
        }
        recipe.setIngredients(ingredients);
        recipes.put(id, recipe);
        return recipe;
    }

    @Override
    public void deleteRecipe(int id) throws NotFoundElementException {
        if (!recipes.containsKey(id)) {
            throw new NotFoundElementException();
        }
        recipes.remove(id);
    }

    @Override
    public List<Recipe> findIngredient(List<Integer> ingredientIds) {
        return recipes.entrySet().stream().map(
                        e -> e.getValue()).filter(
                        recipe -> recipe.getIngredients().stream()
                                .filter(ingredient -> ingredientIds.contains(ingredient.getId())).count() != 0)
                .collect(Collectors.toList());
    }
}
