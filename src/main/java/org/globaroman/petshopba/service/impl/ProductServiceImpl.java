package org.globaroman.petshopba.service.impl;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.ProductMapper;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.globaroman.petshopba.service.ProductService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AmazonS3Service amazonS3Service;

    @Override
    public ProductResponseDto create(CreateRequestProductDto requestProductDto) {
        Product product = productMapper.toModel(requestProductDto);
        String urlImage = amazonS3Service.uploadImage(
                requestProductDto.getImage(),
                getStringObjectKey(requestProductDto));
        product.setImage(urlImage);
        return productMapper.toDto(productRepository.save(product));
    }

    private String getStringObjectKey(CreateRequestProductDto requestProductDto) {
        Random random = new Random();
        int nameImage = random.nextInt(10000000);
        String brand = requestProductDto.getBrand();
        String nameProduct = requestProductDto.getName();

        return brand + "_" + nameProduct + "_" + nameImage + ".jpg";
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
        Product product = getProductFromDb(id);
        amazonS3Service.deleteImage(product.getImage());
        productRepository.deleteById(id);
    }

    private Product getProductFromDb(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("can not find product with id: " + id)
        );
    }
}
