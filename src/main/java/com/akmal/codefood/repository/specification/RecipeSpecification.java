package com.akmal.codefood.repository.specification;

import com.akmal.codefood.api.search.RecipeSearchForm;
import com.akmal.codefood.constant.SearchOperation;
import com.akmal.codefood.entity.Recipe;
import org.springframework.util.StringUtils;

public class RecipeSpecification extends BaseSearchSpecification<Recipe, RecipeSearchForm> {
    public static RecipeSpecification create(RecipeSearchForm form) {
        RecipeSpecification specification = new RecipeSpecification();
        specification.buildSpecification(form);
        return specification;
    }

    @Override
    protected void buildSpecification(RecipeSearchForm form) {
        if (!StringUtils.isEmpty(form.getQ())) {
            add(new SearchCriteria("name", form.getQ(), SearchOperation.MATCH));
        }

        if (form.getCategoryId() != null) {
            add(new SearchCriteria("category.id", form.getCategoryId(), SearchOperation.EQUAL));
        }
    }
}
