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

    @Query("SELECT p FROM Product p JOIN p.animals a JOIN  p.categories c WHERE a.id = :animalId "
            + "AND c.id = :categoryId")
    List<Product> findAllByAnimalsAndCategories(
            @Param("animalId") Long animalId,
            @Param("categoryId") Long categoryId,
            Pageable pageable);

    @Query("SELECT count(p) FROM Product p")
    Long countAllProducts();

    Long countProductsByCategoriesId(@Param("id") Long id);

    Long countProductsByAnimalsId(@Param("id") Long id);

    @Query("SELECT count(p) FROM Product p JOIN p.animals a JOIN  p.categories c "
            + "WHERE a.id = :animalId "
            + "AND c.id = :categoryId")
    Long countProductsByAnimalsIdAndCategoriesId(
            @Param("animalId") Long animalId,
            @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :fromPrice and :toPrice")
    List<Product> findByPriceFromTo(@Param("fromPrice") String fromPrice,
                                    @Param("toPrice") String toPrice,
                                    Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.animals a WHERE a.id = :animalId and p.price "
            + "BETWEEN :fromPrice and :toPrice")
    List<Product> findByPriceByAnimalFromTo(@Param("animalId") Long animalId,
                                            @Param("fromPrice") String fromPrice,
                                            @Param("toPrice") String toPrice);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId and p.price "
            + "BETWEEN :fromPrice and :toPrice")
    List<Product> findByPriceByCategoryFromTo(@Param("categoryId") Long categoryId,
                                            @Param("fromPrice") String fromPrice,
                                            @Param("toPrice") String toPrice);

    @Query("select count(p) from Product p where lower(p.name) "
            + "like lower(concat('%', :name, '%') )")
    Long countByName(@Param("name") String name);

    @Query("select count(p) from Product p where lower(p.brand) "
            + " like lower(concat('%', :brand, '%') )")
    Long countByBrand(@Param("brand") String brand);

    @Query("SELECT count(p) FROM Product p WHERE p.price BETWEEN :fromPrice and :toPrice")
    Long countPriceAllProducts(@Param("fromPrice") String fromPrice,
                               @Param("toPrice") String toPrice);

    @Query("SELECT count(p) FROM Product p JOIN p.animals a WHERE a.id = :animalId and p.price "
            + "BETWEEN :fromPrice and :toPrice")
    Long countByAnimalAndPrice(@Param("animalId") Long animalId,
                               @Param("fromPrice") String fromPrice,
                               @Param("toPrice") String toPrice);

    @Query("SELECT count(p) FROM Product p JOIN p.categories c WHERE c.id = :categoryId and p.price"
            + " BETWEEN :fromPrice and :toPrice")
    Long countCategoryAndPrice(@Param("categoryId") Long categoryId,
                               @Param("fromPrice") String fromPrice,
                               @Param("toPrice") String toPrice);
}
