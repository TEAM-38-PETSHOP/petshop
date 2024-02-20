package org.globaroman.petshopba.repository;

import java.util.List;
import org.globaroman.petshopba.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategories_Id(Long id);

    List<Product> findAllByAnimals_Id(Long id);
}
