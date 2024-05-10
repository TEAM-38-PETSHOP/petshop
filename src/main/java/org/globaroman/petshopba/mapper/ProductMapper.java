package org.globaroman.petshopba.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;
import org.globaroman.petshopba.dto.ordercart.CartItemRequestDto;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "animals", source = "animals", qualifiedByName = "mapAnimals")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategories")
    ProductResponseDto toDto(Product product);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "animals", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entryDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "productNameId", ignore = true)
    Product toModel(CreateRequestProductDto createRequestProductDto);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "animals", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entryDate", ignore = true)
    @Mapping(target = "productNameId", ignore = true)
    Product toUpdate(CreateRequestProductDto requestProductDto, @MappingTarget Product product);

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

    @Named("productFromId")
    default Long productFromId(Product product) {
        return Optional.ofNullable(product)
                .map(Product::getId)
                .orElse(null);
    }

//    @Named("productFromDto")
//    default Product productFromDto(CartItemRequestDto requestDto) {
//        return new Product().
//    }

    @Named("getNameFromProduct")
    default String getNameFromProduct(Product product) {

        return product != null ? product.getName() : null;
    }

    @Named("getPriceFromProduct")
    default BigDecimal getPriceFromProduct(Product product) {
        return product != null ? product.getPrice() : null;
    }

    @Named("mapCategories")
    default List<ResponseCategoryDto> mapCategories(List<Category> categories) {
        return categories.stream()
                .map(this::mapCategoryToResponseDto)
                .collect(Collectors.toList());
    }

    @Named("mapAnimals")
    default List<ResponseAnimalDto> mapAnimals(List<Animal> animals) {
        return animals.stream()
                .map(this::mapAnimalToResponseDto)
                .collect(Collectors.toList());
    }

    default ResponseAnimalDto mapAnimalToResponseDto(Animal animal) {
        ResponseAnimalDto responseAnimalDto = new ResponseAnimalDto();
        responseAnimalDto.setAnimalId(animal.getId());
        responseAnimalDto.setName(animal.getName());
        responseAnimalDto.setAnimalNameId(animal.getAnimalNameId());

        return responseAnimalDto;
    }

    default ResponseCategoryDto mapCategoryToResponseDto(Category category) {
        ResponseCategoryDto responseCategoryDto = new ResponseCategoryDto();
        responseCategoryDto.setCategoryId(category.getId());
        responseCategoryDto.setName(category.getName());
        responseCategoryDto.setDescription(category.getDescription());
        responseCategoryDto.setCategoryNameId(category.getCategoryNameId());
        return responseCategoryDto;
    }
}
