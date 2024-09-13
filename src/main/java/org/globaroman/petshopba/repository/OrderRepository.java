package org.globaroman.petshopba.repository;

import java.util.List;
import org.globaroman.petshopba.model.cartorder.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long id);

    List<Order> findByUserId(Long id);
}
