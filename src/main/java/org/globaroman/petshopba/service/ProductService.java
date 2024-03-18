package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.globaroman.petshopba.dto.product.SimpleSearchProductParameter;

public interface ProductService {
    ProductResponseDto create(CreateRequestProductDto requestProductDto);

    List<ProductResponseDto> getAll();

    ProductResponseDto getById(Long id);

    ProductResponseDto update(Long id, CreateRequestProductDto requestProductDto);

    void delete(Long id);

    List<ProductResponseDto> getAllProductsByCategoryId(Long id);

    List<ProductResponseDto> getAllProductsByAnimalId(Long id);

    List<ProductResponseDto> search(ProductSearchParameters productSearchParameters);

    List<ProductResponseDto> searchByName(SimpleSearchProductParameter productParameter);

    List<ProductResponseDto> searchByBrand(SimpleSearchProductParameter productParameter);
}
