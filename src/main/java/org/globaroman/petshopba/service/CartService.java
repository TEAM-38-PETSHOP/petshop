package org.globaroman.petshopba.service;

import org.globaroman.petshopba.dto.cart.CartItemRequestDto;
import org.globaroman.petshopba.dto.cart.ShoppingCartResponseDto;
import org.springframework.security.core.Authentication;

public interface CartService {
    ShoppingCartResponseDto addProduct(CartItemRequestDto requestDto);

    ShoppingCartResponseDto update(Long cartItemId, CartItemRequestDto requestDto);

    void deleteById(Long cartItemId);

    ShoppingCartResponseDto getShoppingCart(Authentication authentication);
}
