package org.globaroman.petshopba.service;

import org.globaroman.petshopba.dto.wishlist.CreateWishItemRequestDto;
import org.globaroman.petshopba.dto.wishlist.WishListResponseDto;
import org.springframework.security.core.Authentication;

public interface WishListService {
    WishListResponseDto addProduct(CreateWishItemRequestDto requestDto,
                                   Authentication authentication);

    WishListResponseDto getWishList(Authentication authentication);

    void deleteById(Long wishItemId);
}
