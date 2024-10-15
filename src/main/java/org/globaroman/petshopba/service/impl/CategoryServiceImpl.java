package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.CategoryMapper;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.repository.CategoryRepository;
import org.globaroman.petshopba.service.CategoryService;
import org.globaroman.petshopba.service.TransliterationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final TransliterationService transliterationService;

    @Override
    public ResponseCategoryDto create(CreateRequestCategoryDto requestCategory) {
        Category category = categoryMapper.toModel(requestCategory);
        category.setCategoryNameId(
                transliterationService.getLatinStringLine(category.getName()));
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<ResponseCategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public ResponseCategoryDto getById(Long id) {
        Category category = getCategoryFromDb(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public ResponseCategoryDto update(Long id, CreateRequestCategoryDto requestCategoryDto) {
        Category category = getCategoryFromDb(id);
        Category updateCategory = categoryMapper.toUpdate(requestCategoryDto, category);
        updateCategory.setCategoryNameId(
                transliterationService.getLatinStringLine(updateCategory.getName()));
        return categoryMapper.toDto(categoryRepository.save(updateCategory));
    }

    @Override
    public boolean delete(Long id) {
        return categoryRepository.findById(id)
                .map(c -> {
                    categoryRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }

    private Category getCategoryFromDb(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Can not find category with id: " + id);
                    return new EntityNotFoundCustomException(
                            "Can not find category with id: " + id);
                });
    }
}
