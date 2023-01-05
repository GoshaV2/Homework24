package ru.tiunov.homeworkapp.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tiunov.homeworkapp.services.IngredientFileService;
import ru.tiunov.homeworkapp.services.RecipeFileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    private final RecipeFileService recipeFileService;
    private final IngredientFileService ingredientFileService;

    public FileController(RecipeFileService recipeFileService, IngredientFileService ingredientFileService) {
        this.recipeFileService = recipeFileService;
        this.ingredientFileService = ingredientFileService;
    }

    @GetMapping("/export/recipe")
    public ResponseEntity<InputStreamResource> exportRecipeDataFile() {
        try {
            InputStreamResource inputStreamResource = recipeFileService.getInputStreamOfRecipeData();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeData.json\"")
                    .body(inputStreamResource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importRecipeDataFile(@RequestParam MultipartFile file) {
        try {
            recipeFileService.importRecipeData(file.getInputStream());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/export/ingredient")
    public ResponseEntity<InputStreamResource> exportIngredientDataFile() {
        try {
            InputStreamResource inputStreamResource = ingredientFileService.getInputStreamOfIngredientData();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeData.json\"")
                    .body(inputStreamResource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importIngredientDataFile(@RequestParam MultipartFile file) {
        try {
            ingredientFileService.importIngredientData(file.getInputStream());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/export/recipe/forReading")
    public ResponseEntity<InputStreamResource> getRecipeForReading() {
        try {
            InputStreamResource inputStreamResource = recipeFileService.getInputStreamOfRecipeForReading();
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeForReading.txt\"")
                    .body(inputStreamResource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
