package com.akmal.codefood.repository.specification;

import com.akmal.codefood.api.search.ServeSearchForm;
import com.akmal.codefood.constant.SearchOperation;
import com.akmal.codefood.entity.Serve;
import org.springframework.util.StringUtils;

public class ServeSpecification extends BaseSearchSpecification<Serve, ServeSearchForm> {
    public static ServeSpecification create(ServeSearchForm form) {
        ServeSpecification specification = new ServeSpecification();
        specification.buildSpecification(form);
        return specification;
    }

    @Override
    protected void buildSpecification(ServeSearchForm form) {
        add(new SearchCriteria("user.username", form.getUsername(), SearchOperation.EQUAL));

        if (form.getIsReviewed().equals(Boolean.TRUE)) {
            add(new SearchCriteria("reaction", SearchOperation.NOT_NULL));
        }
        if (!StringUtils.isEmpty(form.getQ())) {
            add(new SearchCriteria("recipe.name", form.getQ(), SearchOperation.MATCH));
        }

        if (form.getCategoryId() != null) {
            add(new SearchCriteria("recipe.category.id", form.getCategoryId(), SearchOperation.EQUAL));
        }
    }
}
