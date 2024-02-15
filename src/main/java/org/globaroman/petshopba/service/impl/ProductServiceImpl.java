package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
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

    @Override
    public ProductResponseDto getById(Long id) {
        Product product = getProductFromDb(id);
        return productMapper.toDto(product);
    }

    @Override
    public ProductResponseDto update(Long id,
                                     CreateRequestProductDto requestProductDto) {
        Product product = getProductFromDb(id);
        Product updateProduct = productMapper.toUpdate(requestProductDto, product);
        return productMapper.toDto(productRepository.save(updateProduct));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private Product getProductFromDb(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("can not find product with id: " + id)
        );
    }
}
