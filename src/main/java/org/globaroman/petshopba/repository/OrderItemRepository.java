package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.cartorder.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
