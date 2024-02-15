package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;
import org.globaroman.petshopba.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@SuppressWarnings("unmappedTargetProperties")
@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    ResponseCategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toModel(CreateRequestCategoryDto createRequestCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toUpdate(CreateRequestCategoryDto requestCategoryDto,
                      @MappingTarget Category category);
}
