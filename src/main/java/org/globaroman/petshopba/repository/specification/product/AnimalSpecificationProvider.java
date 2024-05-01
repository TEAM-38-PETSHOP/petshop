package org.globaroman.petshopba.repository.specification.product;

import jakarta.persistence.criteria.Join;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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
            return animalJoin.get("animalNameId").in((Object[]) params);
        };
    }
}

