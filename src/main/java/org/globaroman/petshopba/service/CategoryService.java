package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponceCategoryDto;

public interface CategoryService {
    ResponceCategoryDto create(CreateRequestCategoryDto requestCategory);

    List<ResponceCategoryDto> getAll();

    ResponceCategoryDto getById(Long id);

    ResponceCategoryDto update(Long id, CreateRequestCategoryDto requestCategoryDto);

    void delete(Long id);
}
