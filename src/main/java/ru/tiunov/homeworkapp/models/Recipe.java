package ru.tiunov.homeworkapp.models;

import lombok.Data;

import java.util.List;


@Data
public class Recipe {
    private int id;
    private String title;
    private int readyTime;
    private List<Ingredient> ingredients;
    private List<Integer> ingredientIds; //подскажите как правильно, обычно делается dto?
    private List<String> steps;
}
