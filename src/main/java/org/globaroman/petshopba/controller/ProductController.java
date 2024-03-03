package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://*")
@Tag(name = "Product management",
        description = "endpoint for product management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product",
            description = "You can create a new product")
    public ProductResponseDto create(@RequestBody CreateRequestProductDto requestProductDto) {
        return productService.create(requestProductDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products",
            description = "You can get all products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a product by its ID",
            description = "You can get a product by its ID")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products by category ID",
            description = "You can get all products by category ID")
    public List<ProductResponseDto> getProductsByCategoryId(@PathVariable Long id) {
        return productService.getAllProductsByCategoryId(id);
    }

    @GetMapping("/animals/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products by animal ID",
            description = "You can get all products by animal ID")
    public List<ProductResponseDto> getProductsByAnimalId(@PathVariable Long id) {
        return productService.getAllProductsByAnimalId(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update product",
            description = "You can update a product")
    public ProductResponseDto updateProductById(
            @PathVariable Long id,
            @RequestBody CreateRequestProductDto requestProductDto) {
        return productService.update(id, requestProductDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete product",
            description = "You can delete a product")
    public void deleteProductById(@PathVariable Long id) {
        productService.delete(id);
    }
}
