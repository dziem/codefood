package com.akmal.codefood.entity.dto;

import com.akmal.codefood.entity.Serve;
import com.akmal.codefood.entity.Step;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServeDto implements DtoAble<Serve> {
    private String id;
    @NotNull(message = "Invalid target serving")
    @Min(value = 1, message = "Target serving minimum 1")
    private Integer nServing;
    @NotNull(message = "Invalid recipe id")
    private Long recipeId;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String reaction;
    private String status;
    private String recipeName;
    private String recipeCategoryName;
    private String recipeImage;
    private Integer nStep;
    private Integer nStepDone;
    private List<StepDto> steps;
    private Date createdAt;
    private Date updatedAt;
    private UserDto userDto;

    @Override
    public Serve fromDto() {
        Serve serve = new Serve();
        serve.setNServing(this.getNServing());
        serve.setCreatedAt(new Date());
        serve.setUpdatedAt(new Date());
        serve.setNStepDone(0);
        return serve;
    }

    @Override
    public void toDto(Serve entity) {
        this.setId(entity.getId());
        this.setNServing(entity.getNServing());
        this.setRecipeId(entity.getRecipe().getId());
        this.setReaction(entity.getReaction());
        this.setNStep(entity.getRecipe().getSteps().size());
        this.setNStepDone(entity.getNStepDone());
        this.setStatus(this.getProgress(this.getNStep(), this.getNStepDone()));
        if (entity.getReaction() == null && this.getStatus().equals("done")) {
            this.setStatus("need-rating");
        }
        this.setRecipeName(entity.getRecipe().getName());
        this.setRecipeCategoryName(entity.getRecipe().getCategory().getName());
        this.setRecipeImage(entity.getRecipe().getImage());
        List<StepDto> stepDtos = new ArrayList<>();
        for (Step step: entity.getRecipe().getSteps()) {
            StepDto stepDto = new StepDto();
            stepDto.toDto(step);
            stepDto.setDone(stepDtos.size() + 1 <= this.getNStepDone());
            stepDtos.add(stepDto);
        }
        this.setSteps(stepDtos);
        this.setCreatedAt(entity.getCreatedAt());
        this.setUpdatedAt(entity.getUpdatedAt());
    }

    private String getProgress(Integer nStep, Integer nStepDone) {
        return (nStepDone < nStep) ? "progress" : "done";
    }
}
