package com.akmal.codefood.entity.dto;

import com.akmal.codefood.entity.Category;

import java.util.Date;

public class CategoryDto extends BaseDto<Category> {
    @Override
    public Category fromDto() {
        Category category = new Category();
        category.setName(this.getName());
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        return category;
    }

    @Override
    public Category fromDto(Category entity) {
        entity.setName(this.getName());
        entity.setUpdatedAt(new Date());
        return entity;
    }
}
