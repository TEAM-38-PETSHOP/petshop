package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.globaroman.petshopba.model.cartorder.OrderItem;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = {ProductMapper.class})
public interface OrderItemMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(ResponseOrderItemDto responseOrderItemDto);

    @Mapping(source = "product", target = "productId", qualifiedByName = "productFromId")
    ResponseOrderItemDto toDto(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem partialUpdate(
            ResponseOrderItemDto responseOrderItemDto,
            @MappingTarget OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "product", qualifiedByName = "getPriceFromProduct")
    OrderItem cartItemToOrderItem(CartItem cartItem);
}
