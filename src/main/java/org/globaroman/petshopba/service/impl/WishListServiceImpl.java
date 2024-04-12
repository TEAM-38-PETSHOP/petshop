package org.globaroman.petshopba.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.wishlist.WishItemRequestDto;
import org.globaroman.petshopba.dto.wishlist.WishListResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.WishListMapper;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.WishItem;
import org.globaroman.petshopba.model.WishList;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.repository.WishItemRepository;
import org.globaroman.petshopba.repository.WishListRepository;
import org.globaroman.petshopba.service.WishListService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final WishItemRepository wishItemRepository;
    private final ProductRepository productRepository;
    private final WishListMapper wishListMapper;

    @Override
    public WishListResponseDto addProduct(WishItemRequestDto requestDto,
                                          Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        WishList wishList = getWishLIstFromDbOrNew(user);

        Product requestProduct = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Can't find product with id: " + requestDto.getProductId()));

        if (isProduct(wishList, requestProduct)) {
            return wishListMapper.toDto(wishList);
        }

        WishItem wishItem = new WishItem();

        wishItem.setProduct(requestProduct);
        wishItem.setWishList(wishList);

        WishItem savedWishItem = wishItemRepository.save(wishItem);

        wishList.getWishItems().add(savedWishItem);

        wishListRepository.save(wishList);

        return wishListMapper.toDto(wishList);
    }

    @Override
    public WishListResponseDto getWishList(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        WishList wishList = getWishLIstFromDbOrNew(user);

        return wishListMapper.toDto(wishList);
    }

    @Override
    public void deleteById(Long wishItemId) {
        wishItemRepository.deleteById(wishItemId);
    }

    private boolean isProduct(WishList wishList, Product requestProduct) {
        Set<WishItem> items = wishList.getWishItems();

        Optional<Boolean> isProductPresent = items.stream()
                .map(WishItem::getProduct)
                .map(product -> product.equals(requestProduct))
                .filter(Boolean::booleanValue)
                .findFirst();

        return isProductPresent.orElse(false);
    }

    private WishList getWishLIstFromDbOrNew(User user) {
        return wishListRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    WishList newWishList = new WishList();
                    newWishList.setWishItems(new HashSet<>());
                    newWishList.setUser(user);
                    return wishListRepository.save(newWishList);
                });
    }
}

