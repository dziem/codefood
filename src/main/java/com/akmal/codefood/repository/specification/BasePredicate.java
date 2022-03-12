package com.akmal.codefood.repository.specification;

import com.akmal.codefood.constant.SearchOperation;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class BasePredicate {

    public List<Predicate> predicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<SearchCriteria> list) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        getCriteria(root, criteria.getKey()), criteria.getValue()));
            }
            if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(getCriteria(root, criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
            if (criteria.getOperation().equals(SearchOperation.NOT_NULL)) {
                predicates.add(builder.isNotNull(
                        getCriteria(root, criteria.getKey())));
            }
        }

        return predicates;
    }

    private <Y> Path<Y> getCriteria(Root<?> root, String criteria) {
        String[] fields = criteria.split("\\.");
        Path<Y> path = root.get(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            String field = fields[i];
            path = path.get(field);
        }

        return path;
    }
}
