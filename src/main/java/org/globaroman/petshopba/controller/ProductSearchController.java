package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.CountParameterDto;
import org.globaroman.petshopba.dto.product.PriceSearchParameter;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.globaroman.petshopba.dto.product.SimpleSearchProductParameter;
import org.globaroman.petshopba.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/search")
@Tag(name = "Search Product management",
        description = "endpoint for searching for product using various parameters")
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "Search products by parameters",
            description = "Search for products by brand, name, breedSize, countryProduct, "
                    + "type, group and animals(nameID), categories(nameId) "
                    + "as parameter in different combinations")
    public List<ProductResponseDto> search(ProductSearchParameters productSearchParameters,
                                           Pageable pageable) {
        return productService.search(productSearchParameters, pageable);
    }

    @GetMapping("/count")
    @Operation(summary = "Count all products by parameters")
    public CountParameterDto searchAllCount(ProductSearchParameters productSearchParameters) {
        return productService.searchAllCount(productSearchParameters);
    }

    @GetMapping("/name")
    @Operation(summary = "Search products by name",
            description = "Search products by name")
    public List<ProductResponseDto> searchByName(SimpleSearchProductParameter productParameter,
                                                 Pageable pageable) {
        return productService.searchByName(productParameter, pageable);
    }

    @GetMapping("/name/count")
    @Operation(summary = "Count products by name")
    public CountParameterDto searchByNameCount(SimpleSearchProductParameter productParameter) {
        return productService.searchByNameCount(productParameter);
    }

    @GetMapping("/brand")
    @Operation(summary = "Search products by brand",
            description = "Search products by brand")
    public List<ProductResponseDto> searchByBrand(SimpleSearchProductParameter productParameter,
                                                  Pageable pageable) {
        return productService.searchByBrand(productParameter, pageable);
    }

    @GetMapping("/brand/count")
    @Operation(summary = "Count products by brand")
    public CountParameterDto searchByBrandCount(SimpleSearchProductParameter productParameter) {
        return productService.searchByBrandCount(productParameter);
    }

    @GetMapping("/price")
    @Operation(summary = "Search products by price ",
            description = "Search products by price as from price to price /api/products"
                    + "/search/price?from=10&to=100")
    public List<ProductResponseDto> searchByPrice(PriceSearchParameter priceSearchParameter,
                                                  Pageable pageable) {
        return productService.searchByPrice(priceSearchParameter, pageable);
    }

    @GetMapping("/price/count")
    @Operation(summary = "Count products by price  /api/products/search/price/count?from=10&to=100")
    public CountParameterDto searchByPrice(PriceSearchParameter priceSearchParameter) {
        return productService.searchByPriceCount(priceSearchParameter);
    }

    @GetMapping("/price/animal/{animalId}")
    @Operation(summary = "Search products by animal and price ",
            description = "Search products by animal and price as from price to price")
    public List<ProductResponseDto> searchByAnimalAndPrice(@PathVariable Long animalId,
                                                           PriceSearchParameter parameter,
                                                           Pageable pageable) {
        return productService.searchByAnimalAndPrice(animalId, parameter, pageable);
    }

    @GetMapping("/price/animal/{animalId}/count")
    @Operation(summary = "Count products by animal and price /api/products/search/price"
            + "/animal/1/count?from=10&to=100")
    public CountParameterDto searchByAnimalAndPriceCount(@PathVariable Long animalId,
                                                         PriceSearchParameter parameter) {
        return productService.searchByAnimalAndPriceCount(animalId, parameter);
    }

    @GetMapping("/price/category/{categoryId}")
    @Operation(summary = "Search products by category and price ",
            description = "Search products by category and price as from price to price")
    public List<ProductResponseDto> searchByCategoryAndPrice(@PathVariable Long categoryId,
                                                             PriceSearchParameter parameter,
                                                             Pageable pageable) {
        return productService.searchByCategoryAndPrice(categoryId, parameter, pageable);
    }

    @GetMapping("/price/category/{categoryId}/count")
    @Operation(summary = "Count products by category and price"
            + "/api/products/search/price/category/1/count?from=10&to=100")
    public CountParameterDto searchByCategoryAndPriceCount(@PathVariable Long categoryId,
                                                           PriceSearchParameter parameter) {
        return productService.searchByCategoryAndPriceCount(categoryId, parameter);
    }
}
