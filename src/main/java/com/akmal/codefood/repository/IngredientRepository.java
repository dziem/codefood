package com.akmal.codefood.repository;

import com.akmal.codefood.entity.Ingredient;
import com.akmal.codefood.entity.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends OptionalHandlingRepository<Ingredient, Long> {
    void deleteByIdNotInAndRecipe(List<Long> ids, Recipe recipe);
}
