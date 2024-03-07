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
    @Mapping(source = "product", target = "productId", qualifiedByName = "productFromId")
    @Mapping(source = "product", target = "productName", qualifiedByName = "getNameFromProduct")
    CarItemResponseDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartItem toUpdate(CartItemRequestDto requestDto, @MappingTarget CartItem cartItem);

}
