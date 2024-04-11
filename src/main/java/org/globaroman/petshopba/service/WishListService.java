package org.globaroman.petshopba.service;

import org.globaroman.petshopba.dto.wishlist.WishItemRequestDto;
import org.globaroman.petshopba.dto.wishlist.WishListResponseDto;
import org.springframework.security.core.Authentication;

public interface WishListService {
    WishListResponseDto addProduct(WishItemRequestDto requestDto, Authentication authentication);

    WishListResponseDto getWishList(Authentication authentication);

    void deleteById(Long wishItemId);
}
