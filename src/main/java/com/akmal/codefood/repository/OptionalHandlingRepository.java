package com.akmal.codefood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OptionalHandlingRepository<T, ID> extends JpaRepository<T, ID> {
    T getById(ID var1, String entityName);
}
