package com.akmal.codefood.repository;

import com.akmal.codefood.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends OptionalHandlingRepository<Category, Long> {
}
