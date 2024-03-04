package org.globaroman.petshopba.service;

import org.globaroman.petshopba.dto.cart.CartItemRequestDto;
import org.globaroman.petshopba.dto.cart.ShoppingCartResponseDto;
import org.springframework.security.core.Authentication;

public interface CartService {
    ShoppingCartResponseDto addProduct(CartItemRequestDto requestDto, Authentication authentication);

    ShoppingCartResponseDto update(Long cartItemId, CartItemRequestDto requestDto, Authentication authentication);

    void deleteById(Long cartItemId, Authentication authentication);

    ShoppingCartResponseDto getShoppingCart(Authentication authentication);
}
