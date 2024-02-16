package org.globaroman.petshopba.service.impl;

import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.cart.CartItemRequestDto;
import org.globaroman.petshopba.dto.cart.ShoppingCartResponseDto;
import org.globaroman.petshopba.mapper.CartItemMapper;
import org.globaroman.petshopba.mapper.ShoppingCartMapper;
import org.globaroman.petshopba.repository.CartItemRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.globaroman.petshopba.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto addProduct(CartItemRequestDto requestDto) {
        return null;
    }

    @Override
    public ShoppingCartResponseDto update(Long cartItemId, CartItemRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteById(Long cartItemId) {

    }

    @Override
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        return null;
    }
}
