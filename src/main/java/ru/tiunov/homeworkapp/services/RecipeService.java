package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe) throws NotFoundElementException;

    Recipe getRecipeById(int id) throws NotFoundElementException;

    List<Recipe> getRecipes(int page);

    Recipe updateRecipe(int id, Recipe recipe) throws NotFoundElementException;

    void deleteRecipe(int id) throws NotFoundElementException;

    List<Recipe> findIngredient(List<Integer> ingredientIds);
}
