package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;
import org.globaroman.petshopba.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    @Mapping(target = "categoryId", source = "id")
    ResponseCategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "categoryNameId", ignore = true)
    Category toModel(CreateRequestCategoryDto createRequestCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "categoryNameId", ignore = true)
    Category toUpdate(CreateRequestCategoryDto requestCategoryDto,
                      @MappingTarget Category category);
}
