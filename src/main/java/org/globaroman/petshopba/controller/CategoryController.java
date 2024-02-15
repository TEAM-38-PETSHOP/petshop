package org.globaroman.petshopba.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponceCategoryDto;
import org.globaroman.petshopba.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponceCategoryDto create(@RequestBody CreateRequestCategoryDto requestCategory) {
        return categoryService.create(requestCategory);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponceCategoryDto> getAllCategory() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponceCategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PatchMapping("/{id}")

    public ResponceCategoryDto updateCategoryById(
            @PathVariable Long id,
            @RequestBody CreateRequestCategoryDto requestCategoryDto) {
        return categoryService.update(id, requestCategoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
