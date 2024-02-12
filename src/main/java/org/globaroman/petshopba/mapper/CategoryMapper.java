package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponceCategoryDto;
import org.globaroman.petshopba.model.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    ResponceCategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toModel(CreateRequestCategoryDto createRequestCategory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CreateRequestCategoryDto createRequestCategory,
                           @MappingTarget Category category);
}
