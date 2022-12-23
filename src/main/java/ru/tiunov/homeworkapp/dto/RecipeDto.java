package ru.tiunov.homeworkapp.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.models.Recipe;

import java.util.List;

@Data
public class RecipeDto {
    private int id;
    private String title;
    private int readyTime;
    private List<IngredientDto> ingredients;
    private List<String> steps;
}
