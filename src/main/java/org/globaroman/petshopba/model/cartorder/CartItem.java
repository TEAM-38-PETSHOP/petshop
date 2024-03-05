package org.globaroman.petshopba.model.cartorder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.globaroman.petshopba.model.Product;

@Entity
@Table(name = "cart_items")
@Data
@EqualsAndHashCode(exclude = {"product","shoppingCart"})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
}
