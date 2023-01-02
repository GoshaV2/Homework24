package ru.tiunov.homeworkapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tiunov.homeworkapp.exceptions.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;
import ru.tiunov.homeworkapp.services.RecipeService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Управление рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Получение всех рецептов",
            description = "Получение идёт по страницам, в одной странице 10 рецептов"
    )
    @Parameters(
            value = {
                    @Parameter(name = "page", example = "1", description = "Номер страницы")
            }
    )
    @GetMapping
    public ResponseEntity getRecipes(@RequestParam(name = "page", defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(recipeService.getRecipes(page));
    }

    @Operation(
            summary = "Поиск рецептов",
            description = "Поиск осуществляется по id ингридиентов"
    )
    @Parameters(
            value = {
                    @Parameter(name = "ingredientId", example = "1;2;3;", description = "Список id указывается через ';', либо одно число")
            }
    )
    @GetMapping("/findByIngredientId")
    public ResponseEntity findRecipesByIngredientId(@RequestParam("ingredientId") String ingredientIds) {
        List<Integer> ingredientIdList = Arrays.stream(ingredientIds.split(";"))
                .map(e -> Integer.parseInt(e)).collect(Collectors.toList());
        return ResponseEntity.ok(recipeService.findIngredient(ingredientIdList));
    }

    @Operation(summary = "Получение рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт найден", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity getRecipe(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeById(id));
        } catch (NotFoundElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Добавление рецепта")
    @Parameters(
            value = {
                    @Parameter(name = "Данные о рецепте", schema = @Schema(implementation = Recipe.class))
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Успешно создан и возращён рецепт", content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )}),
                    @ApiResponse(responseCode = "422", description = "Не был найден рецепт")
            }
    )
    @PostMapping
    public ResponseEntity addRecipe(@RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok(recipeService.addRecipe(recipe));
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Изменить рецепт по id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Успешно обновлен", content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }),
                    @ApiResponse(responseCode = "422", description = "Не был найден рецепт")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity updateRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Удалить рецепт по id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Успешно удален"),
                    @ApiResponse(responseCode = "422", description = "Не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") int id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
