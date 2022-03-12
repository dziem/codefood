package com.akmal.codefood.api.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSearchForm {
    private String q;
    private Long categoryId;
}
