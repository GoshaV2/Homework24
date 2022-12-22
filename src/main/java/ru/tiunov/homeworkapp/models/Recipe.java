package ru.tiunov.homeworkapp.models;

import lombok.Data;

import java.util.List;


@Data
public class Recipe {
    private String title;
    private int readyTime;
    private List<Integer> ingredientIds;
    private List<String> steps;
}
