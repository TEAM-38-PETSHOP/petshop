package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Add new order",
            description = "You can creat a new order")
    public ResponseOrderDto addOrder(
            @RequestBody CreateOrderRequestDto requestDto,
            Authentication authentication) {
        return orderService.addOrder(requestDto, authentication);
    }

    @GetMapping
    @Operation(summary = "Get all order",
            description = "You can get all order as history")
    public List<ResponseOrderDto> getAllOrder(Authentication authentication) {
        return orderService.getAllOrder(authentication);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update status order",
            description = "You can update an order")
    public ResponseOrderDto updateOrderStatus(
            @RequestBody OrderStatusDto statusDto,
            @PathVariable Long id) {
        return orderService.updateStatusToOrder(statusDto, id);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get all item by order",
            description = "You can get all orders from specific order")
    public List<ResponseOrderItemDto> getOrderItemsFromOrder(@PathVariable Long orderId) {
        return orderService.getOrderItensFromOrder(orderId);
    }

    @GetMapping("/items/{itemId}")
    @Operation(summary = "Get item by order",
            description = "You can get order from specific order")
    public ResponseOrderItemDto getOrderItemById(
            @PathVariable Long itemId) {
        return orderService.getOrderItemById(itemId);
    }
}
