package com.akmal.codefood.entity.dto;

import com.akmal.codefood.entity.Ingredient;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class IngredientDto implements DtoAble<Ingredient> {
    @NotBlank(message = "Item is mandatory")
    private String item;
    @NotBlank(message = "Unit is mandatory")
    private String unit;
    @NotEmpty(message = "Value is mandatory")
    private Integer value;

    @Override
    public Ingredient fromDto() {
        Ingredient ingredient = new Ingredient();
        ingredient.setItem(this.getItem());
        ingredient.setUnit(this.getUnit());
        ingredient.setValue(this.getValue());
        return ingredient;
    }

    @Override
    public void toDto(Ingredient entity) {
        this.setItem(entity.getItem());
        this.setUnit(entity.getUnit());
        this.setValue(getValue());
    }
}
