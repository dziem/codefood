package com.akmal.codefood.controller;

import com.akmal.codefood.api.EmptyJsonResponse;
import com.akmal.codefood.entity.dto.CategoryDto;
import com.akmal.codefood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("recipe-categories")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return ok(categoryService.list());
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CategoryDto form) {
        return ok(categoryService.create(form), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody CategoryDto form) {
        return ok(categoryService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ok(new EmptyJsonResponse());
    }
}
