package com.akmal.codefood.service;

import com.akmal.codefood.entity.Category;
import com.akmal.codefood.entity.dto.CategoryDto;
import com.akmal.codefood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CrudService<CategoryDto> {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> list() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.toDto(category);
            categoryDtos.add(categoryDto);
        });
        return categoryDtos;
    }

    public Category getEntity(Long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public CategoryDto create(CategoryDto dto) {
        Category category = dto.fromDto();
        categoryRepository.save(category);
        dto.toDto(category);
        return dto;
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = categoryRepository.getById(id);
        dto.fromDto(category);
        categoryRepository.save(category);
        dto.toDto(category);
        return dto;
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.delete(category);
    }
}
