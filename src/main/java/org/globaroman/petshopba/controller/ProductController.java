package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.CountParameterDto;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.dto.product.RequestUpdateImageToProductDto;
import org.globaroman.petshopba.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product management",
        description = "endpoint for product management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
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
    public List<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productService.getAll(pageable);
    }

    @GetMapping("/count")
    public CountParameterDto countGetAllProducts() {
        return productService.countAllProducts();
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
    public List<ProductResponseDto> getProductsByCategoryId(@PathVariable Long id,
                                                            Pageable pageable) {
        return productService.getAllProductsByCategoryId(id, pageable);
    }

    @GetMapping("/categories/{id}/count")
    public CountParameterDto getCountProductsByCategoryId(@PathVariable Long id) {
        return productService.countProductsByCategoryId(id);
    }

    @GetMapping("/animals/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products by animal ID",
            description = "You can get all products by animal ID")
    public List<ProductResponseDto> getProductsByAnimalId(@PathVariable Long id,
                                                          Pageable pageable) {
        return productService.getAllProductsByAnimalId(id, pageable);
    }

    @GetMapping("/animals/{id}/count")
    public CountParameterDto getCountProductsByAnimalId(@PathVariable Long id) {
        return productService.countProductsByAnimalId(id);
    }

    @GetMapping("/animal/{animalId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all products by animal ID and category ID",
            description = "You can get all products by animal ID and category ID")
    public List<ProductResponseDto> getAllProductsAnimalAndCategory(
            @PathVariable Long animalId,
            @PathVariable Long categoryId,
            Pageable pageable) {
        return productService.getAllProductsByAnimalAndCategory(animalId, categoryId, pageable);

    }

    @GetMapping("/animal/{animalId}/category/{categoryId}/count")
    public CountParameterDto getCountAllProductsAnimalAndCategory(@PathVariable Long animalId,
                                                                  @PathVariable Long categoryId) {
        return productService.countProductsByAnimalAndCategory(animalId, categoryId);
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random amount of product",
            description = "You can get random amount of product. Default quantity - 15, "
                    + "You can indicate your quantity items")
    public List<ProductResponseDto> getRandomProducts(@RequestParam(defaultValue = "15")
                                                          int count) {
        return productService.getRandomProducts(count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update product",
            description = "You can update a product")
    public ProductResponseDto updateProductById(
            @PathVariable Long id,
            @RequestBody CreateRequestProductDto requestProductDto) {

        return productService.update(id, requestProductDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updateImage/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update images",
            description = "You can add images to products")
    public ProductResponseDto updateImageOfProductById(
            @PathVariable Long id,
            @RequestBody RequestUpdateImageToProductDto requestImageDto) {

        return productService.updateImageToProduct(id, requestImageDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete product",
            description = "You can delete a product")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        boolean isDelete = productService.delete(id);

        if (isDelete) {
            return ResponseEntity.ok("Product was deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Try again");
        }
    }

    @GetMapping("/admin/updateNameId")
    public void updateNameId() {
        productService.updateNameId();
    }
}
