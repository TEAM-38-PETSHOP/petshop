package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.ordercart.CartItemRequestDto;
import org.globaroman.petshopba.dto.ordercart.CreateCartItemRequestDto;
import org.globaroman.petshopba.dto.ordercart.ShoppingCartResponseDto;
import org.globaroman.petshopba.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management",
        description = "endpoint for shopping cart management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new product - create shopping cart",
            description = "You can create shoppingCart and add new product to your cart")
    public ShoppingCartResponseDto addProductCreateCart(
            @RequestBody CreateCartItemRequestDto requestDtos,
            Authentication authentication) {
        return cartService.addProduct(requestDtos, authentication);
    }

    @PatchMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update new quantity products",
            description = "You can update quantity products into your cart")
    public ShoppingCartResponseDto update(
            @RequestBody CartItemRequestDto requestDto,
            @PathVariable Long cartItemId,
            Authentication authentication) {
        return cartService.update(cartItemId, requestDto, authentication);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete cartItem",
            description = "You can delete your cartItem by its ID from cart")
    public ResponseEntity<String> delete(@PathVariable Long cartItemId) {
        boolean isDelete = cartService.deleteById(cartItemId);
        if (isDelete) {
            return ResponseEntity.ok("CartItem was deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Try again");
        }

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get shopping cart",
            description = "You can get all selected products from your cart")
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        return cartService.getShoppingCart(authentication);
    }
}
