package org.globaroman.petshopba.repository;

import java.util.List;
import org.globaroman.petshopba.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    List<Product> findAllByCategories_Id(Long id, Pageable pageable);

    List<Product> findAllByAnimals_Id(Long id, Pageable pageable);

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :name, '%') )")
    List<Product> findByName(@Param("name") String productParameter, Pageable pageable);

    @Query("select p from Product p where lower(p.brand) like lower(concat('%', :brand, '%') )")
    List<Product> findByBrand(@Param("brand")String parameter, Pageable pageable);

    @Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT :countProduct",
            nativeQuery = true)
    List<Product> findRandomProducts(@Param("countProduct") int countProduct);
}
