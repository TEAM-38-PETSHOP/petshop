package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.wishlist.WishItemResponseDto;
import org.globaroman.petshopba.model.WishItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = ProductMapper.class)
public interface WishItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wishList", ignore = true)
    @Mapping(target = "product", ignore = true)
    WishItem toEntity(WishItemResponseDto wishItemResponseDto);

    @Mapping(target = "wishItemId", source = "id")
    WishItemResponseDto toDto(WishItem wishItem);
}
