package org.globaroman.petshopba.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification getSpecification(String[] params);
}
