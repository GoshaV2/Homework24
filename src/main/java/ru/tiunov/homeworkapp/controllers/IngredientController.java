package ru.tiunov.homeworkapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Ingredient;
import ru.tiunov.homeworkapp.services.IngredientService;

import java.io.IOException;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity getIngredients() {
        return ResponseEntity.ok(ingredientService.getIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity getIngredientById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(ingredientService.getIngredientById(id));
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping
    public ResponseEntity createIngredient(@RequestBody Ingredient ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.createIngredient(ingredient));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredient));
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteIngredient(@PathVariable("id") int id) {
        try {
            ingredientService.deleteIngredient(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }
}
