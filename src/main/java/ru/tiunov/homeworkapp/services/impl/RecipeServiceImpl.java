package ru.tiunov.homeworkapp.services.impl;

import org.springframework.stereotype.Service;
import ru.tiunov.homeworkapp.dto.IngredientDto;
import ru.tiunov.homeworkapp.dto.RecipeDto;
import ru.tiunov.homeworkapp.exception.NotFoundElementException;
import ru.tiunov.homeworkapp.models.Recipe;
import ru.tiunov.homeworkapp.services.IngredientService;
import ru.tiunov.homeworkapp.services.RecipeFileService;
import ru.tiunov.homeworkapp.services.RecipeService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private Map<Integer, RecipeDto> recipeDtoMap;
    private final IngredientService ingredientService;

    private final RecipeFileService recipeFileService;
    private static int lastRecipeId = 0;

    public RecipeServiceImpl(IngredientService ingredientService, RecipeFileService recipeFileService) {
        recipeDtoMap = new TreeMap<>();
        this.ingredientService = ingredientService;
        this.recipeFileService = recipeFileService;
    }

    @PostConstruct
    public void init() {
        try {
            recipeFileService.initRecipeService(this);
            initializeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeData() throws IOException {
        recipeDtoMap = recipeFileService.readRecipeMap();
        Optional<Integer> maxId=recipeDtoMap.keySet().stream().max(Integer::compare);
        maxId.ifPresent(integer -> lastRecipeId = integer);
    }

    private void saveRecipeMap() throws IOException {
        recipeFileService.writeRecipeMap(recipeDtoMap);
    }

    @Override
    public RecipeDto addRecipe(Recipe recipe) throws NotFoundElementException, IOException {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setReadyTime(recipe.getReadyTime());
        recipeDto.setId(++lastRecipeId);
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for (int id : recipe.getIngredientIds()) {
            ingredientDtoList.add(ingredientService.getIngredientById(id));
        }
        recipeDto.setIngredients(ingredientDtoList);
        recipeDtoMap.put(recipeDto.getId(), recipeDto);
        saveRecipeMap();
        return recipeDto;
    }

    @Override
    public RecipeDto getRecipeById(int id) throws NotFoundElementException {
        if (recipeDtoMap.containsKey(id)) {
            return recipeDtoMap.get(id);
        }
        throw new NotFoundElementException();
    }

    @Override
    public List<RecipeDto> getRecipes(int page) {
        List<RecipeDto> recipeList = new ArrayList<>(recipeDtoMap.values());
        if (recipeList.size() < (page - 1) * 10 + 1) {
            return new ArrayList<>();
        }
        if (recipeList.size() < (page - 1) * 10 + 10) {
            return recipeList.subList((page - 1) * 10, recipeList.size());
        }
        return recipeList.subList((page - 1) * 10, (page - 1) * 10 + 10);
    }

    @Override
    public RecipeDto updateRecipe(int id, Recipe recipe) throws NotFoundElementException, IOException {
        if (!recipeDtoMap.containsKey(id)) {
            throw new NotFoundElementException();
        }
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(id);
        List<IngredientDto> ingredients = new ArrayList<>();
        for (int ingredientId : recipe.getIngredientIds()) {
            ingredients.add(ingredientService.getIngredientById(ingredientId));
        }
        recipeDto.setReadyTime(recipe.getReadyTime());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setSteps(recipe.getSteps());
        recipeDto.setIngredients(ingredients);
        recipeDtoMap.put(id, recipeDto);
        saveRecipeMap();
        return recipeDto;
    }

    @Override
    public void deleteRecipe(int id) throws NotFoundElementException, IOException {
        if (!recipeDtoMap.containsKey(id)) {
            throw new NotFoundElementException();
        }
        saveRecipeMap();
        recipeDtoMap.remove(id);
    }

    @Override
    public List<RecipeDto> findIngredient(List<Integer> ingredientIds) {
        return recipeDtoMap.values().stream().filter(
                        recipe -> recipe.getIngredients().stream()
                                .filter(ingredient -> ingredientIds.contains(ingredient.getId())).count() == ingredientIds.size())
                .collect(Collectors.toList());
    }

}
