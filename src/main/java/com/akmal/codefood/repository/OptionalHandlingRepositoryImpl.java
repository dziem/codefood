package com.akmal.codefood.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalHandlingRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements OptionalHandlingRepository<T, ID> {
    public OptionalHandlingRepositoryImpl(JpaEntityInformation entityInformation,
                                          EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public T getById(ID id) {
        Optional<T> t = findById(id);
        if (!t.isPresent()) {
            throw new NoSuchElementException("No value present for ID: " + id);
        }
        return t.get();
    }
}
