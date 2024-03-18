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

        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(searchParameters.price()));
        }

        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("countryProduct")
                    .getSpecification(searchParameters.countryProduct()));
        }

        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("group")
                    .getSpecification(searchParameters.group()));
        }

        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("breedSize")
                    .getSpecification(searchParameters.breedSize()));
        }

        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("type")
                    .getSpecification(searchParameters.type()));
        }

        if (searchParameters.price() != null && searchParameters.price().length > 0) {
            spec = spec.and(productSpecificationProviderManager
                    .getSpecificationProvider("packaging")
                    .getSpecification(searchParameters.packaging()));
        }

        return spec;
    }
}
