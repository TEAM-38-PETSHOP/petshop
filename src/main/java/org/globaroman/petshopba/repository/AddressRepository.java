package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.cartorder.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
