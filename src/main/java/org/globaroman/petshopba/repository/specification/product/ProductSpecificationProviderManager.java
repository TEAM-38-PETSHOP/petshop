package org.globaroman.petshopba.repository.specification.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.specification.SpecificationProvider;
import org.globaroman.petshopba.repository.specification.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductSpecificationProviderManager implements SpecificationProviderManager<Product> {

    private final List<SpecificationProvider<Product>> productSpecificationProvider;

    @Override
    public SpecificationProvider<Product> getSpecificationProvider(String key) {
        return productSpecificationProvider.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find correct specification provider for key " + key));
    }
}
