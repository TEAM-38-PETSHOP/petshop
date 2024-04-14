package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.wishlist.WishListResponseDto;
import org.globaroman.petshopba.model.WishList;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = WishItemMapper.class)
public interface WishListMapper {

    @Mapping(target = "user", ignore = true)
    WishList toEntity(WishListResponseDto wishListResponseDto);

    @AfterMapping
    default void linkWishItems(@MappingTarget WishList wishList) {
        wishList.getWishItems().forEach(wishItem -> wishItem.setWishList(wishList));
    }

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "wishItems", target = "wishItems")
    WishListResponseDto toDto(WishList wishList);
}
