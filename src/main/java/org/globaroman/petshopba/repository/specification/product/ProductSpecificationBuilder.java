package org.globaroman.petshopba.repository.specification.product;

import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.specification.SpecificationBuilder;
import org.globaroman.petshopba.repository.specification.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSpecificationBuilder implements SpecificationBuilder<Product> {

    private final SpecificationProviderManager<Product> productSpecificationProviderManager;

    @Override
    public Specification<Product> build(ProductSearchParameters searchParameters) {
        Specification<Product> spec = Specification.where(null);

        if (searchParameters.name() != null && searchParameters.name().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("name")
                    .getSpecification(searchParameters.name()));
        }

        if (searchParameters.brand() != null && searchParameters.brand().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("brand")
                    .getSpecification(searchParameters.brand()));
        }


        if (searchParameters.countryProduct() != null
                && searchParameters.countryProduct().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("countryProduct")
                    .getSpecification(searchParameters.countryProduct()));
        }

        if (searchParameters.group() != null && searchParameters.group().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("group")
                    .getSpecification(searchParameters.group()));
        }

        if (searchParameters.breedSize() != null && searchParameters.breedSize().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("breedSize")
                    .getSpecification(searchParameters.breedSize()));
        }

        if (searchParameters.type() != null && searchParameters.type().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("type")
                    .getSpecification(searchParameters.type()));
        }

        if (searchParameters.animals() != null && searchParameters.animals().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("animals")
                    .getSpecification(searchParameters.animals()));
        }

        if (searchParameters.categories() != null && searchParameters.categories().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("categories")
                    .getSpecification(searchParameters.categories()));
        }

        if (searchParameters.productNameId() != null && searchParameters.productNameId()
                .length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("productNameId")
                    .getSpecification(searchParameters.productNameId()));
        }

        return spec;
    }
}
