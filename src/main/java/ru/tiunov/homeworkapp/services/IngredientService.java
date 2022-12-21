package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;


import java.util.List;

public interface IngredientService {

    List<Ingredient> getIngredients();

    Ingredient getIngredientById(int id) throws NotFoundElementException;

    Ingredient createIngredient(Ingredient ingredient);

    Ingredient updateIngredient(int id, Ingredient ingredient) throws NotFoundElementException;

    void deleteIngredient(int id) throws NotFoundElementException;
}
