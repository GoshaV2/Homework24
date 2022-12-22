package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;

import java.util.List;

public interface RecipeService {
    RecipeDto addRecipe(Recipe recipe) throws NotFoundElementException;

    RecipeDto getRecipeById(int id) throws NotFoundElementException;

    List<RecipeDto> getRecipes(int page);

    RecipeDto updateRecipe(int id, Recipe recipe) throws NotFoundElementException;

    void deleteRecipe(int id) throws NotFoundElementException;

    List<RecipeDto> findIngredient(List<Integer> ingredientIds);
}
