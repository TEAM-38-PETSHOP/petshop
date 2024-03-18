package org.globaroman.petshopba.repository.specification;

import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(ProductSearchParameters searchParameters);
}
