package com.akmal.codefood.service;

import com.akmal.codefood.entity.Ingredient;
import com.akmal.codefood.entity.Recipe;
import com.akmal.codefood.entity.dto.IngredientDto;
import com.akmal.codefood.entity.dto.RecipeDto;
import com.akmal.codefood.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> bulkCreate(Recipe recipe, RecipeDto dto) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto ingredientDto: dto.getIngredientsPerServing()) {
            Ingredient ingredient = ingredientDto.fromDto();
            ingredient.setRecipe(recipe);
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);
        return ingredients;
    }

    public void bulkUpdate(Recipe recipe, RecipeDto dto) {
        List<Long> ids = new ArrayList<>();
        List<Ingredient> steps = new ArrayList<>();
        int count = 0;
        for (IngredientDto ingredientDto: dto.getIngredientsPerServing()) {
            Ingredient ingredient = new Ingredient();
            if (count < recipe.getIngredients().size()) {
                ingredient = recipe.getIngredients().get(count);
                ids.add(ingredient.getId());
            }
            ingredient.setValue(ingredientDto.getValue());
            ingredient.setUnit(ingredientDto.getUnit());
            ingredient.setItem(ingredientDto.getItem());
            ingredient.setRecipe(recipe);
            steps.add(ingredient);
            count++;
        }
        ingredientRepository.deleteByIdNotInAndRecipe(ids, recipe);
        ingredientRepository.saveAll(steps);
    }
}
