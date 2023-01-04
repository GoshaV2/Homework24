package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.exception.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;

import java.io.IOException;
import java.util.List;

public interface IngredientService {

    List<IngredientDto> getIngredients();

    IngredientDto getIngredientById(int id) throws NotFoundElementException;

    IngredientDto createIngredient(Ingredient ingredient) throws IOException;

    IngredientDto updateIngredient(int id, Ingredient ingredient) throws NotFoundElementException, IOException;

    void deleteIngredient(int id) throws NotFoundElementException, IOException;

    void initializeData() throws IOException;
}
