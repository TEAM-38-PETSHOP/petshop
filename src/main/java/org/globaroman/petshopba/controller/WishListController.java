package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.wishlist.WishItemRequestDto;
import org.globaroman.petshopba.dto.wishlist.WishListResponseDto;
import org.globaroman.petshopba.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wish list management",
        description = "endpoint for wish list (favorite products) management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile/wishlists")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new product - create wish list",
            description = "You can create wish list and add new product to your wish list")
    public WishListResponseDto addProductCreateCart(
            @RequestBody WishItemRequestDto requestDto,
            Authentication authentication) {
        return wishListService.addProduct(requestDto, authentication);
    }

    @DeleteMapping("/wish-items/{wishItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete wishItem from list",
            description = "You can delete your wishItem by its ID from list")
    public void delete(@PathVariable Long wishItemId) {
        wishListService.deleteById(wishItemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get wish list",
            description = "You can get all selected products from wish list")
    public WishListResponseDto getShoppingCart(Authentication authentication) {
        return wishListService.getWishList(authentication);
    }
}
