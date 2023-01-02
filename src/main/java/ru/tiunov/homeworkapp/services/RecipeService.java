package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;

import java.io.IOException;
import java.util.List;

public interface RecipeService {
    RecipeDto addRecipe(Recipe recipe) throws NotFoundElementException, IOException;

    RecipeDto getRecipeById(int id) throws NotFoundElementException;

    List<RecipeDto> getRecipes(int page);

    RecipeDto updateRecipe(int id, Recipe recipe) throws NotFoundElementException, IOException;

    void deleteRecipe(int id) throws NotFoundElementException, IOException;

    List<RecipeDto> findIngredient(List<Integer> ingredientIds);
}
