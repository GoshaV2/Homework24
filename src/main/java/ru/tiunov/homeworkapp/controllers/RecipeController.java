package ru.tiunov.homeworkapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;
import ru.tiunov.homeworkapp.services.RecipeService;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity getRecipe(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok(recipeService.getRecipe(id));
        } catch (NotFoundElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/recipe")
    public ResponseEntity addRecipe(@RequestBody Recipe recipe){
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok().build();
    }
}
