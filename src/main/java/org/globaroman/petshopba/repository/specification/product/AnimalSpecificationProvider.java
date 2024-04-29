package org.globaroman.petshopba.repository.specification.product;

import jakarta.persistence.criteria.*;
import org.globaroman.petshopba.model.*;
import org.globaroman.petshopba.repository.specification.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.stereotype.*;

@Component
public class AnimalSpecificationProvider implements SpecificationProvider<Product> {
    @Override
    public String getKey() {
        return "animals";
    }

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Animal> animalJoin = root.join("animals");
            return animalJoin.get("name").in((Object[]) params);
        };
    }
}

