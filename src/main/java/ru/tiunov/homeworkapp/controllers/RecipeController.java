package ru.tiunov.homeworkapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;
import ru.tiunov.homeworkapp.services.RecipeService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity getRecipes(@RequestParam(name = "page", defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(recipeService.getRecipes(page));
    }

    @GetMapping("/findByIngredientId")
    public ResponseEntity findRecipesByIngredientId(@RequestParam("ingredientId") String ingredientIds) {
        List<Integer> ingredientIdList = Arrays.stream(ingredientIds.split(";"))
                .map(e -> Integer.parseInt(e)).collect(Collectors.toList());
        return ResponseEntity.ok(recipeService.findIngredient(ingredientIdList));
    }

    @GetMapping("/{id}")
    public ResponseEntity getRecipe(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeById(id));
        } catch (NotFoundElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity addRecipe(@RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok(recipeService.addRecipe(recipe));
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") int id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
