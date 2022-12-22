package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;


import java.util.List;

public interface IngredientService {

    List<IngredientDto> getIngredients();

    IngredientDto getIngredientById(int id) throws NotFoundElementException;

    IngredientDto createIngredient(Ingredient ingredient);

    IngredientDto updateIngredient(int id, Ingredient ingredient) throws NotFoundElementException;

    void deleteIngredient(int id) throws NotFoundElementException;
}
