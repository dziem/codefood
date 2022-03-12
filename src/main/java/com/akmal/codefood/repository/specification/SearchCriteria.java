package com.akmal.codefood.repository.specification;

import com.akmal.codefood.constant.SearchOperation;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;

    /**
     * To list many to one relation, use dot as separator. For example, to list City that belongs to Province DKI Jakarta, add
     * new SearchCriteria("province.name", "DKI Jakarta", SearchOperation.EQUAL);
     *
     * @param key
     * @param value
     * @param operation
     */
    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public SearchCriteria(String key, SearchOperation operation) {
        this.key = key;
        this.operation = operation;
    }
}
