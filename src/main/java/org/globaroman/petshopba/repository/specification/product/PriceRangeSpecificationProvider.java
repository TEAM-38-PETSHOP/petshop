package org.globaroman.petshopba.repository.specification.product;

import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceRangeSpecificationProvider implements SpecificationProvider<Product> {
    @Override
    public String getKey() {
        return "price";
    }

    @Override
    public Specification<Product> getSpecification(String[] param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"),
                param[0], param[1]);
    }
}
