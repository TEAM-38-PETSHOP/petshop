package org.globaroman.petshopba.mapper;

import java.util.List;
import java.util.Set;
import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.ordercart.CarItemResponseDto;
import org.globaroman.petshopba.dto.ordercart.ShoppingCartResponseDto;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartResponseDto toShoppingCartDto(ShoppingCart shoppingCart);

    List<CarItemResponseDto> cartItemsToDtos(Set<CartItem> cartItems);
}
