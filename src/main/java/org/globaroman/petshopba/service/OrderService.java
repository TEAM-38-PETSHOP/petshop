package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.springframework.security.core.Authentication;

public interface OrderService {
    ResponseOrderDto addOrder(CreateOrderRequestDto requestDto, Authentication authentication);

    List<ResponseOrderDto> getAllOrder(Authentication authentication);

    ResponseOrderDto updateStatusToOrder(OrderStatusDto statusDto, Long id);

    List<ResponseOrderItemDto> getOrderItensFromOrder(Long orderId);

    ResponseOrderItemDto getOrderItemById(Long orderId);
}
