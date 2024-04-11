package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
}
