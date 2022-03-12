package com.akmal.codefood.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredients")
public class Ingredient extends RecipeDetail {
    @Column(name = "item")
    private String item;

    @Column(name = "unit")
    private String unit;

    @Column(name = "value")
    private Integer value;
}
