package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.mapper.ProductMapper;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.service.ProductService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto create(CreateRequestProductDto requestProductDto) {
        Product product = productMapper.toModel(requestProductDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDto> getAll() {

        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }
}
