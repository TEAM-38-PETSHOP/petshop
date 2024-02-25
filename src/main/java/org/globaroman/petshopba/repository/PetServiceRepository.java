package org.globaroman.petshopba.repository;

import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.model.groom.PetService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetServiceRepository extends JpaRepository<PetService, Long> {

    @Query("SELECT p FROM PetService p ORDER BY p.numberList ASC")
    List<PetService> findAllSortedNumberList();

    @Query("SELECT p FROM PetService p where p.numberList = :petServiceId")
    Optional<PetService> findByNumberId(@Param("petServiceId") Long petServiceId);

    @Query("SELECT p FROM PetService p where p.animal.id = :animalId ORDER By p.numberList ASC")
    List<PetService> findAllByAnimalId(@Param("animalId") Long animalId);
}
