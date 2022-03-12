package com.akmal.codefood.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseSearchSpecification<T, FORM> implements Specification<T> {
    protected List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        BasePredicate basePredicate = new BasePredicate();
        List<Predicate> predicates = basePredicate.predicate(root, criteriaQuery, criteriaBuilder, list);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    protected abstract void buildSpecification(FORM form);
}
