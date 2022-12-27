package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.models.Ingredient;

import java.util.Map;

public interface IngredientFileService {
    Map<Integer, IngredientDto> readIngredientMap();

    void writeMapIngredient(Map<Integer, IngredientDto> ingredientMap);
}
