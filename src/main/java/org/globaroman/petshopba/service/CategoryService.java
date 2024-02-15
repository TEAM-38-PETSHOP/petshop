package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;

public interface CategoryService {
    ResponseCategoryDto create(CreateRequestCategoryDto requestCategory);

    List<ResponseCategoryDto> getAll();

    ResponseCategoryDto getById(Long id);

    ResponseCategoryDto update(Long id, CreateRequestCategoryDto requestCategoryDto);

    void delete(Long id);
}
