package org.globaroman.petshopba.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {

    ProductResponseDto toDto(Product product);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "animals", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toModel(CreateRequestProductDto createRequestProductDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(CreateRequestProductDto createRequestProductDto,
                          @MappingTarget Product product);

    @AfterMapping
    default void setCategories(
            CreateRequestProductDto requestDto,
            @MappingTarget Product product) {
        List<Category> categories = requestDto
                .getCategoriesId().stream()
                .map(Category::new)
                .collect(Collectors.toList());
        product.setCategories(categories);
    }

    @AfterMapping
    default void setAnimals(
            CreateRequestProductDto requestDto,
            @MappingTarget Product product) {
        List<Animal> animals = requestDto
                .getAnimalsId().stream()
                .map(Animal::new)
                .collect(Collectors.toList());
        product.setAnimals(animals);
    }
}