package org.globaroman.petshopba.repository;

import java.util.List;
import org.globaroman.petshopba.model.groom.TypePetService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TypePetServiceRepository extends JpaRepository<TypePetService, Long> {

    @Query("SELECT p FROM TypePetService p where p.posluga.id = :serviceId "
            + "ORDER By p.numberList ASC")
    List<TypePetService> findAllByPetServiceId(@Param("serviceId") Long serviceId);
}
