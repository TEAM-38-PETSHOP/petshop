package org.globaroman.petshopba.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDto create(@RequestBody CreateRequestProductDto requestProductDto) {
        return productService.create(requestProductDto);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAll();
    }
}
