package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.ordercart.CarItemResponseDto;
import org.globaroman.petshopba.dto.ordercart.CartItemRequestDto;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = ProductMapper.class)
public interface CartItemMapper {

    @Mapping(target = "cartItemId", source = "id")
    @Mapping(target = "productDto", source = "product")
    CarItemResponseDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartItem toUpdate(CartItemRequestDto requestDto, @MappingTarget CartItem cartItem);
}
