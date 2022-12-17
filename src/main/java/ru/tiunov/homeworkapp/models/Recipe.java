package ru.tiunov.homeworkapp.models;

import lombok.Data;

import java.util.List;


@Data
public class Recipe {
    private String title;
    private int readyTime;
    private List<Ingredient> ingredients;
    private List<String> steps;
}
