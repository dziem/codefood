package com.akmal.codefood.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "recipe_steps")
public class Step extends RecipeDetail implements Comparable<Step> {
    @Column(name = "stepOrder")
    private Integer stepOrder;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Override
    public int compareTo(Step s) {
        if (getStepOrder() == null || s.getStepOrder() == null) {
            return 0;
        }
        return getStepOrder().compareTo(s.getStepOrder());
    }
}
