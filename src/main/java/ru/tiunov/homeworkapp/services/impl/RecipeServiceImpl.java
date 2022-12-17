package ru.tiunov.homeworkapp.services.impl;

import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;
import ru.tiunov.homeworkapp.services.RecipeService;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final Map<Integer, Recipe> recipes;

    private static int lastRecipeId=0;

    public RecipeServiceImpl() {
        recipes=new HashMap<>();
    }

    @Override
    public void addRecipe(Recipe recipe){
        recipes.put(++lastRecipeId,recipe);
        System.out.println(recipe);
        System.out.println(recipes);
    }

    @Override
    public Recipe getRecipe(int id) throws NotFoundElementException{
        if(recipes.containsKey(id)){
            return recipes.get(id);
        }
        throw new NotFoundElementException();
    }
}
