package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.ordercart.CreateOrderNoNameRequestDto;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    ResponseOrderDto addOrder(CreateOrderRequestDto requestDto, Authentication authentication);

    List<ResponseOrderDto> getAllOrderByUser(Authentication authentication);

    ResponseOrderDto updateStatusToOrder(OrderStatusDto statusDto, Long id);

    List<ResponseOrderItemDto> getOrderItensFromOrder(Long orderId);

    ResponseOrderItemDto getOrderItemById(Long orderId);

    ResponseOrderDto addOrderNoName(CreateOrderNoNameRequestDto requestDto);

    List<ResponseOrderDto> getAllOrderForAdmin(Pageable pageable);

    String deleteOrder(Long id, Authentication authentication);

    ResponseOrderDto updateOrderToCanceled(Long id, Authentication authentication);

    ResponseOrderDto renewOrder(Long orderId, Authentication authentication);
}
