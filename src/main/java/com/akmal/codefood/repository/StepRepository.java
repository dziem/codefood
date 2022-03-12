package com.akmal.codefood.repository;

import com.akmal.codefood.entity.Recipe;
import com.akmal.codefood.entity.Step;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends OptionalHandlingRepository<Step, Long> {
    void deleteByIdNotInAndRecipe(List<Long> ids, Recipe recipe);
}
