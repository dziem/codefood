package com.akmal.codefood.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CompleteStepForm {
    @NotNull
    private Integer stepOrder;
}
