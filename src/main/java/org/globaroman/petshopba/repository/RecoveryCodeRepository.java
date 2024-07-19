package org.globaroman.petshopba.repository;

import java.util.Optional;
import org.globaroman.petshopba.model.user.RecoveryPasswordCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecoveryCodeRepository extends JpaRepository<RecoveryPasswordCode, Long> {

    Optional<RecoveryPasswordCode> findByCode(String code);
}
