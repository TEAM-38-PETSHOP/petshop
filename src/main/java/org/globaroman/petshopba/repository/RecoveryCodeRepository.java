package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.user.RecoveryPasswordCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecoveryCodeRepository extends JpaRepository<RecoveryPasswordCode, Long> {

    Optional<RecoveryPasswordCode> findByCode(String code);
}
