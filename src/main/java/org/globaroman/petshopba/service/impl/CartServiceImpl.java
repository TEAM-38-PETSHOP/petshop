package org.globaroman.petshopba.service.impl;

import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.cart.CarItemResponseDto;
import org.globaroman.petshopba.dto.cart.CartItemRequestDto;
import org.globaroman.petshopba.dto.cart.ShoppingCartResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.CartItemMapper;
import org.globaroman.petshopba.mapper.ShoppingCartMapper;
import org.globaroman.petshopba.model.CartItem;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.ShoppingCart;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.CartItemRepository;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.globaroman.petshopba.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto addProduct(CartItemRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ShoppingCart shoppingCart = getShoppingCartFromDbOrNew(user);
        Product requestProduct = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Can't find product with id: " + requestDto.getProductId()));
        CartItem cartItem = new CartItem();
        cartItem.setProduct(requestProduct);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setShoppingCart(shoppingCart);

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        shoppingCart.getCartItems().add(savedCartItem);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto update(Long cartItemId, CartItemRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return null;
    }

    @Override
    public void deleteById(Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ShoppingCart shoppingCart = getShoppingCartFromDbOrNew(user);

        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }


    private ShoppingCart getShoppingCartFromDbOrNew(User user) {
        return shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    ShoppingCart newShoppingCart = new ShoppingCart();
                    newShoppingCart.setUser(user);
                    return shoppingCartRepository.save(newShoppingCart);
                });
    }
}
