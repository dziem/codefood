package com.akmal.codefood.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CompleteStepForm {
    @NotNull(message = "stepOrder is required")
    private Integer stepOrder;
}
