package com.akmal.codefood.service;

import com.akmal.codefood.api.search.RecipeSearchForm;
import com.akmal.codefood.entity.Category;
import com.akmal.codefood.entity.Ingredient;
import com.akmal.codefood.entity.Recipe;
import com.akmal.codefood.entity.Step;
import com.akmal.codefood.entity.dto.RecipeDto;
import com.akmal.codefood.entity.dto.RecipeListDto;
import com.akmal.codefood.entity.dto.StepDto;
import com.akmal.codefood.repository.RecipeRepository;
import com.akmal.codefood.repository.specification.RecipeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RecipeService implements CrudService<RecipeDto> {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StepService stepService;

    @Autowired
    private IngredientService ingredientService;

    public RecipeListDto list(Pageable pageable, RecipeSearchForm searchForm) {
        RecipeSpecification recipeSpecification = RecipeSpecification.create(searchForm);
        Page<Recipe> recipes = recipeRepository.findAll(recipeSpecification, pageable);
        List<RecipeDto> recipeDtos = recipes.map(recipe -> {
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.toDto(recipe);
            recipeDto.setIngredientsPerServing(null);
            recipeDto.setSteps(null);
            return recipeDto;
        }).getContent();
        RecipeListDto recipeListDto = new RecipeListDto();
        recipeListDto.setTotal(recipeDtos.size());
        recipeListDto.setRecipes(recipeDtos);
        return recipeListDto;
    }

    public Recipe getById(Long id) {
        return recipeRepository.getById(id);
    }

    public RecipeDto detailIngredients(Long id) {
        Recipe recipe = recipeRepository.getById(id);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.toDto(recipe);
        recipeDto.setSteps(null);
        return recipeDto;
    }

    public List<StepDto> detailSteps(Long id) {
        Recipe recipe = recipeRepository.getById(id);
        List<StepDto> stepDtos = stepService.listByRecipe(recipe);
        return stepDtos;
    }

    @Override
    public RecipeDto create(RecipeDto dto) {
        Category category = categoryService.getEntity(dto.getRecipeCategoryId());
        Recipe recipe = dto.fromDto();
        recipe.setCategory(category);
        recipeRepository.save(recipe);
        List<Step> steps = stepService.bulkCreate(recipe, dto);
        recipe.setSteps(steps);
        List<Ingredient> ingredients = ingredientService.bulkCreate(recipe, dto);
        recipe.setIngredients(ingredients);
        dto.toDto(recipe);
        return dto;
    }

    @Transactional
    @Override
    public RecipeDto update(Long id, RecipeDto dto) {
        Recipe recipe = recipeRepository.getById(id);
        dto.fromDto(recipe);
        recipeRepository.save(recipe);
        stepService.bulkUpdate(recipe, dto);
        ingredientService.bulkUpdate(recipe, dto);
        return dto;
    }

    @Transactional
    public void updateReaction(Long id, String reaction) {
        Recipe recipe = recipeRepository.getById(id);
        if (reaction.equals("like")) {
            recipe.setNReactionLike(recipe.getNReactionLike() + 1);
        } else if (reaction.equals("neutral")) {
            recipe.setNReactionNeutral(recipe.getNReactionNeutral() + 1);
        } else if (reaction.equals("dislike")) {
            recipe.setNReactionDislike(recipe.getNReactionDislike() + 1);
        }
        recipeRepository.save(recipe);
    }

    @Override
    public void delete(Long id) {
        Recipe recipe = recipeRepository.getById(id);
        recipeRepository.delete(recipe);
    }
}
