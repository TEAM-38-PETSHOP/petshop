package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponceCategoryDto;
import org.globaroman.petshopba.mapper.CategoryMapper;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.repository.CategoryRepository;
import org.globaroman.petshopba.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponceCategoryDto create(CreateRequestCategoryDto requestCategory) {
        Category category = categoryMapper.toModel(requestCategory);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<ResponceCategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
