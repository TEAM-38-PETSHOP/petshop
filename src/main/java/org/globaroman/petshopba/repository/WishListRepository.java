package org.globaroman.petshopba.repository;

import java.util.Optional;
import org.globaroman.petshopba.model.WishList;
import org.globaroman.petshopba.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUserId(Long id);

    void deleteByUser(User user);
}
