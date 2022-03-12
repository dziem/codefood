package com.akmal.codefood.entity.dto;

import com.akmal.codefood.entity.Step;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StepDto implements DtoAble<Step> {
    @NotEmpty(message = "Step order is mandatory")
    private Integer stepOrder;
    @NotBlank(message = "Step description is mandatory")
    private String description;
    private Boolean done;

    @Override
    public Step fromDto() {
        Step step = new Step();
        step.setStepOrder(this.getStepOrder());
        step.setDescription(this.getDescription());
        return step;
    }

    @Override
    public void toDto(Step entity) {
        this.setStepOrder(entity.getStepOrder());
        this.setDescription(entity.getDescription());
    }
}
