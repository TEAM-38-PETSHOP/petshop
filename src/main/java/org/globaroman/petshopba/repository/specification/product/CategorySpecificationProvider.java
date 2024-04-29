package org.globaroman.petshopba.repository.specification.product;

import jakarta.persistence.criteria.*;
import org.globaroman.petshopba.model.*;
import org.globaroman.petshopba.repository.specification.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

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
