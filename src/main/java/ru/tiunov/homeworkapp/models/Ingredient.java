package ru.tiunov.homeworkapp.models;

import lombok.Data;

@Data
public class Ingredient {
    private String title;
    private int count;
    private String unitOfMeasurement;
}
