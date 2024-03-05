package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Override
    public ResponseOrderDto addOrder(
            CreateOrderRequestDto requestDto,
            Authentication authentication) {
        return null;
    }

    @Override
    public List<ResponseOrderDto> getAllOrder(Authentication authentication) {
        return null;
    }

    @Override
    public ResponseOrderDto updateStatusToOrder(OrderStatusDto statusDto, Long id) {
        return null;
    }

    @Override
    public List<ResponseOrderItemDto> getOrderItensFromOrder(Long orderId) {
        return null;
    }

    @Override
    public ResponseOrderItemDto getOrderItemById(Long orderId, Long itemId) {
        return null;
    }
}
