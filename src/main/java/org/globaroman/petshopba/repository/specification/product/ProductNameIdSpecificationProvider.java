package org.globaroman.petshopba.repository.specification.product;

import java.util.Arrays;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductNameIdSpecificationProvider implements SpecificationProvider<Product> {
    @Override
    public String getKey() {
        return "productNameId";
    }

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("productNameId")
                .in(Arrays.stream(params).toArray());
    }
}
