package com.akmal.codefood.entity.dto;

import com.akmal.codefood.entity.Category;
import com.akmal.codefood.entity.Ingredient;
import com.akmal.codefood.entity.Recipe;
import com.akmal.codefood.entity.Step;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeDto extends BaseDto<Recipe> {
    private CategoryDto recipeCategory;
    @NotNull(message = "recipeCategoryId is required")
    private Long recipeCategoryId;
    @NotBlank(message = "image is required")
    private String image;
    @JsonProperty("nReactionLike")
    private Long nReactionLike;
    @JsonProperty("nReactionNeutral")
    private Long nReactionNeutral;
    @JsonProperty("nReactionDislike")
    private Long nReactionDislike;
    @NotNull(message = "nServing is required")
    private Integer nServing;
    @NotEmpty(message = "steps is required")
    private List<StepDto> steps;
    @NotEmpty(message = "ingredientsPerServing is required")
    private List<IngredientDto> ingredientsPerServing;

    @Override
    public Recipe fromDto() {
        this.setNReactionLike(0L);
        this.setNReactionNeutral(0L);
        this.setNReactionDislike(0L);
        Recipe recipe = new Recipe();
        this.fillRecipe(recipe);
        recipe.setCreatedAt(new Date());
        recipe.setUpdatedAt(new Date());
        return recipe;
    }

    @Override
    public Recipe fromDto(Recipe entity) {
        this.setId(entity.getId());
        this.setNReactionLike(entity.getNReactionLike());
        this.setNReactionNeutral(entity.getNReactionNeutral());
        this.setNReactionDislike(entity.getNReactionDislike());
        this.fillRecipe(entity);
        this.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(new Date());
        this.setUpdatedAt(entity.getUpdatedAt());
        return entity;
    }

    public void toDto(Recipe recipe) {
        super.toDto(recipe);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.toDto(recipe.getCategory());
        this.setRecipeCategory(categoryDto);
        this.setRecipeCategoryId(recipe.getCategory().getId());
        this.setImage(recipe.getImage());
        this.setNReactionLike(recipe.getNReactionLike());
        this.setNReactionNeutral(recipe.getNReactionNeutral());
        this.setNReactionDislike(recipe.getNReactionDislike());
        this.setNServing(recipe.getNServing());
        List<StepDto> stepDtos = new ArrayList<>();
        for (Step step: recipe.getSteps()) {
            StepDto stepDto = new StepDto();
            stepDto.toDto(step);
            stepDto.setId(step.getId());
            stepDto.setRecipeId(recipe.getId());
            stepDtos.add(stepDto);
        }
        this.setSteps(stepDtos);
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        for (Ingredient ingredient: recipe.getIngredients()) {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.toDto(ingredient);
            ingredientDtos.add(ingredientDto);
        }
        this.setIngredientsPerServing(ingredientDtos);
    }

    private void fillRecipe(Recipe recipe) {
        recipe.setName(this.getName());
        Category category = new Category();
        category.setId(this.getRecipeCategoryId());
        recipe.setCategory(category);
        recipe.setImage(this.getImage());
        recipe.setNReactionLike(this.getNReactionLike());
        recipe.setNReactionNeutral(this.getNReactionNeutral());
        recipe.setNReactionDislike(this.getNReactionDislike());
        recipe.setNServing(this.getNServing());
    }
}
