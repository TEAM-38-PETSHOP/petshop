package org.globaroman.petshopba.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.dto.ordercart.CartItemRequestDto;
import org.globaroman.petshopba.dto.ordercart.CreateCartItemRequestDto;
import org.globaroman.petshopba.dto.ordercart.ShoppingCartResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.CartItemMapper;
import org.globaroman.petshopba.mapper.ShoppingCartMapper;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.CartItemRepository;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.globaroman.petshopba.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartServiceImpl implements CartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto addProduct(
            CreateCartItemRequestDto requestDtos,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ShoppingCart shoppingCart = getShoppingCartFromDbOrNew(user);

        List<CartItemRequestDto> cartItemRequestDtos = requestDtos.getCartItemRequestDtos();

        for (CartItemRequestDto requestDto : cartItemRequestDtos) {
            Product requestProduct = productRepository.findById(requestDto.getProductId())
                    .orElseThrow(() -> {
                        log.error("Can't find product with id: " + requestDto.getProductId());
                        return new EntityNotFoundCustomException("Can't find product with id: "
                                + requestDto.getProductId());
                    });

            if (!isProductIntoCart(shoppingCart, requestProduct, requestDto)) {
                CartItem cartItem = new CartItem();

                cartItem.setProduct(requestProduct);
                cartItem.setQuantity(requestDto.getQuantity());
                cartItem.setShoppingCart(shoppingCart);

                CartItem savedCartItem = cartItemRepository.save(cartItem);

                shoppingCart.getCartItems().add(savedCartItem);
            }
        }

        shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto update(
            Long cartItemId,
            CartItemRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> {
                    log.error("Can't find cart with id: "
                            + cartItemId);
                    return new EntityNotFoundCustomException(
                            "Can't find cart with id: " + cartItemId);
                }
        );
        CartItem updateCart = cartItemMapper.toUpdate(requestDto, cartItem);
        cartItemRepository.save(updateCart);
        ShoppingCart shoppingCart = getShoppingCartFromDbOrNew(user);

        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public boolean deleteById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .map(cartItem -> {
                    cartItemRepository.deleteById(cartItemId);
                    return true;
                })
                .orElse(false);
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
                    newShoppingCart.setCartItems(new HashSet<>());
                    newShoppingCart.setUser(user);
                    return shoppingCartRepository.save(newShoppingCart);
                });
    }

    private boolean isProductIntoCart(ShoppingCart shoppingCart,
                                      Product requestProduct,
                                      CartItemRequestDto requestDto) {
        Map<Long, CartItem> cartItemsMap = new HashMap<>();

        for (CartItem item : shoppingCart.getCartItems()) {
            cartItemsMap.put(item.getProduct().getId(), item);
        }

        if (cartItemsMap.containsKey(requestProduct.getId())) {
            CartItem item = cartItemsMap.get(requestProduct.getId());
            item.setQuantity(requestDto.getQuantity());
            cartItemRepository.save(item);
            return true;
        }
        return false;
    }
}
