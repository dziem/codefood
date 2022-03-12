package com.akmal.codefood.repository;

import com.akmal.codefood.entity.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends OptionalHandlingRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
}
