package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.globaroman.petshopba.dto.product.SimpleSearchProductParameter;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDto create(CreateRequestProductDto requestProductDto);

    List<ProductResponseDto> getAll(Pageable pageable);

    ProductResponseDto getById(Long id);

    ProductResponseDto update(Long id, CreateRequestProductDto requestProductDto);

    void delete(Long id);

    List<ProductResponseDto> getAllProductsByCategoryId(Long id,
                                                        Pageable pageable);

    List<ProductResponseDto> getAllProductsByAnimalId(Long id,
                                                      Pageable pageable);

    List<ProductResponseDto> search(ProductSearchParameters productSearchParameters,
                                    Pageable pageable);

    List<ProductResponseDto> searchByName(SimpleSearchProductParameter productParameter,
                                          Pageable pageable);

    List<ProductResponseDto> searchByBrand(SimpleSearchProductParameter productParameter,
                                           Pageable pageable);

    List<ProductResponseDto> getRandomProducts(int count);
}
