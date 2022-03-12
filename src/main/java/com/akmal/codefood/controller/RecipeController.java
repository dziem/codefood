package com.akmal.codefood.controller;

import com.akmal.codefood.api.EmptyJsonResponse;
import com.akmal.codefood.api.search.RecipeSearchForm;
import com.akmal.codefood.entity.dto.RecipeDto;
import com.akmal.codefood.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("recipes")
public class RecipeController extends BaseController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                       @RequestParam(value = "limit", required = false, defaultValue = "10") int size,
                                       @RequestParam(value = "sort", required = false) String sort,
                                       RecipeSearchForm searchForm) {
        boolean asc = false;
        if (StringUtils.isEmpty(sort)) {
            sort = "updatedAt";
        } else if (sort.equals("name_asc")) {
            sort = "name";
            asc = true;
        } else if (sort.equals("name_desc")) {
            sort = "name";
            asc = false;
        }
        Pageable pageable = PageRequest.of(skip, size, Sort.by(getSortBy(sort, asc)));
        if (sort.equals("like_desc")) {
            pageable = PageRequest.of(skip, size, Sort.by(getSortBy("nReactionLike", false))
                    .and(Sort.by(getSortBy("nReactionNeutral", false)))
                    .and(Sort.by((getSortBy("nReactionDislike", true)))));
        }
        return ok(recipeService.list(pageable, searchForm));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detailIngredients(@PathVariable("id") Long id) {
        return ok(recipeService.detailIngredients(id));
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<Object> detailSteps(@PathVariable("id") Long id) {
        return ok(recipeService.detailSteps(id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody RecipeDto form) {
        return ok(recipeService.create(form), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody RecipeDto form) {
        return ok(recipeService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return ok(new EmptyJsonResponse());
    }
}
