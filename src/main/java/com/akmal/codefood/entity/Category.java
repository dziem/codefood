package com.akmal.codefood.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipe_categories")
public class Category extends BaseEntity {
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "recipeCategoryId")
    private List<Recipe> recipes;
}
