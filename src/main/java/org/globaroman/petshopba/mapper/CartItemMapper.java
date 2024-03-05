package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.cart.CarItemResponseDto;
import org.globaroman.petshopba.dto.cart.CartItemRequestDto;
import org.globaroman.petshopba.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = ProductMapper.class)
public interface CartItemMapper {
    @Mapping(source = "product", target = "productId", qualifiedByName = "productFromId")
    @Mapping(source = "product", target = "productName", qualifiedByName = "getNameFromProduct")
    CarItemResponseDto toCartItemDto(CartItem cartItem);

    CartItem toUpdate(CartItemRequestDto requestDto, @MappingTarget CartItem cartItem);
}
