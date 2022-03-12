package com.akmal.codefood.service;

import com.akmal.codefood.entity.Recipe;
import com.akmal.codefood.entity.Step;
import com.akmal.codefood.entity.dto.RecipeDto;
import com.akmal.codefood.entity.dto.StepDto;
import com.akmal.codefood.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StepService {
    @Autowired
    private StepRepository stepRepository;

    public List<Step> bulkCreate(Recipe recipe, RecipeDto dto) {
        List<Step> steps = new ArrayList<>();
        for (StepDto stepDto: dto.getSteps()) {
            Step step = stepDto.fromDto();
            step.setRecipe(recipe);
            steps.add(step);
        }
        stepRepository.saveAll(steps);
        return steps;
    }

    public void bulkUpdate(Recipe recipe, RecipeDto dto) {
        List<Long> ids = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        int count = 0;
        for (StepDto stepDto: dto.getSteps()) {
            Step step = new Step();
            if (count < recipe.getSteps().size()) {
                step = recipe.getSteps().get(count);
                ids.add(step.getId());
            }
            step.setStepOrder(stepDto.getStepOrder());
            step.setDescription(stepDto.getDescription());
            step.setRecipe(recipe);
            steps.add(step);
            count++;
        }
        stepRepository.deleteByIdNotInAndRecipe(ids, recipe);
        stepRepository.saveAll(steps);
    }

    public List<StepDto> listByRecipe(Recipe recipe) {
        List<StepDto> stepDtos = new ArrayList<>();
        Collections.sort(recipe.getSteps());
        for (Step step: recipe.getSteps()) {
            StepDto stepDto = new StepDto();
            stepDto.setStepOrder(step.getStepOrder());
            stepDto.setDescription(step.getDescription());
            stepDtos.add(stepDto);
        }
        return stepDtos;
    }
}
