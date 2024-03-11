package org.globaroman.petshopba.mapper;

import java.util.List;
import java.util.Set;
import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.ordercart.OrderItemDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.model.cartorder.Order;
import org.globaroman.petshopba.model.cartorder.OrderItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = {
        UserMapper.class,
        AddressMapper.class,
        OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "user", target = "userId", qualifiedByName = "idFromUser")
    ResponseOrderDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    Order partialUpdate(ResponseOrderDto responseOrderDto, @MappingTarget Order order);

    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "total", constant = "0.00")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order shoppingCartToOrder(ShoppingCart shoppingCart);

    default OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        Long id = orderItem.getId();
        int quantity = orderItem.getQuantity();
        Long productId = orderItem.getProduct() != null ? orderItem.getProduct().getId() : null;

        return new OrderItemDto(id, productId, quantity);
    }

    List<ResponseOrderItemDto> orderItemsToDtos(Set<OrderItem> orderItems);
}
