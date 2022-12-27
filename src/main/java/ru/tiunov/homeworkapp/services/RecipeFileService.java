package ru.tiunov.homeworkapp.services;

import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.models.Recipe;

import java.util.Map;

public interface RecipeFileService {
    Map<Integer, RecipeDto> readRecipeMap();

    void writeRecipeMap(Map<Integer, RecipeDto> recipeMap);
}
