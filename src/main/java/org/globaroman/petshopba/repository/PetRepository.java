package org.globaroman.petshopba.repository;

import java.util.List;
import org.globaroman.petshopba.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserId(Long userId);
}
