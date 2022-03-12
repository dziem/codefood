package com.akmal.codefood.service;

import java.util.List;

public interface CrudService<D> {
    D create(D dto);
    D update(Long id, D dto);
    void delete(Long id);
}
