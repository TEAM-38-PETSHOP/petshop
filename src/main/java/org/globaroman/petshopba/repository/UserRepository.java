package org.globaroman.petshopba.repository;

import java.util.Optional;
import org.globaroman.petshopba.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
