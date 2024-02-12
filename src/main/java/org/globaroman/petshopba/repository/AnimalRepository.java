package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
