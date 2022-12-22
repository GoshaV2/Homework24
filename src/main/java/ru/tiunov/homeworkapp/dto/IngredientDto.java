package ru.tiunov.homeworkapp.dto;

import lombok.Data;
import ru.tiunov.homeworkapp.models.Ingredient;

import java.util.List;

@Data
public class IngredientDto {
    private int id;
    private String title;
    private int count;
    private String unitOfMeasurement;
}
