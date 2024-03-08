package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
