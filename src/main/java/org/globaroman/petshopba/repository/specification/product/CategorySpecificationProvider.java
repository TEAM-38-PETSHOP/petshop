package org.globaroman.petshopba.repository.specification.product;

import jakarta.persistence.criteria.Join;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Product> {
    @Override
    public String getKey() {
        return "categories";
    }

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("categories");
            return categoryJoin.get("name").in((Object[]) params);
        };
    }
}
