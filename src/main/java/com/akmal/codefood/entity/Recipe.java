package com.akmal.codefood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "recipeCategoryId")
    @JsonIgnore
    private Category category;

    @Column(name = "image")
    private String image;

    @Column(name = "nReactionLike")
    private Long nReactionLike;

    @Column(name = "nReactionNeutral")
    private Long nReactionNeutral;

    @Column(name = "nReactionDislike")
    private Long nReactionDislike;

    @Column(name = "nServing")
    private Integer nServing;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "recipeId")
    private List<Step> steps;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "recipeId")
    private List<Ingredient> ingredients;
}
