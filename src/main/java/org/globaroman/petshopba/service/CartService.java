package org.globaroman.petshopba.service;

import org.globaroman.petshopba.dto.ordercart.CartItemRequestDto;
import org.globaroman.petshopba.dto.ordercart.ShoppingCartResponseDto;
import org.springframework.security.core.Authentication;

public interface CartService {
    ShoppingCartResponseDto addProduct(
            CartItemRequestDto requestDto,
            Authentication authentication);

    ShoppingCartResponseDto update(
            Long cartItemId,
            CartItemRequestDto requestDto,
            Authentication authentication);

    void deleteById(Long cartItemId);

    ShoppingCartResponseDto getShoppingCart(Authentication authentication);
}
