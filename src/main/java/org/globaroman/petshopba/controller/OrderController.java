package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.ordercart.CreateOrderNoNameRequestDto;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.PeriodDataParameterDto;
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
@Tag(name = "Order management",
        description = "endpoint for order management")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new order",
            description = "You can creat a new order")
    public ResponseOrderDto addOrder(
            @RequestBody CreateOrderRequestDto requestDto,
            Authentication authentication) {
        return orderService.addOrder(requestDto, authentication);
    }

    @PostMapping("/withoutAuth")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create order for user without auth",
            description = "Create order for user without auth")
    public ResponseOrderDto addOrderNoName(@Valid @RequestBody
                                               CreateOrderNoNameRequestDto requestDto) {
        return orderService.addOrderNoName(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders for authed user",
            description = "You can get all order as history")
    public List<ResponseOrderDto> getAllOrderByUser(Authentication authentication) {
        return orderService.getAllOrderByUser(authentication);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update status order",
            description = "You can update an order. You need Role - ADMIN")
    public ResponseOrderDto updateOrderStatus(
            @RequestBody OrderStatusDto statusDto,
            @PathVariable Long id) {
        return orderService.updateStatusToOrder(statusDto, id);
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders for admin",
            description = "You can get all orders. You need Role - ADMIN")
    public List<ResponseOrderDto> getAllOrderForAdmin(PeriodDataParameterDto parameterDto) {
        return orderService.getAllOrderForAdmin(parameterDto);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all item by order",
            description = "You can get all orderItem from specific order by its ID")
    public List<ResponseOrderItemDto> getOrderItemsFromOrder(@PathVariable Long orderId) {
        return orderService.getOrderItensFromOrder(orderId);
    }

    @GetMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get item by ID",
            description = "You can get orderItem by its ID")
    public ResponseOrderItemDto getOrderItemById(
            @PathVariable Long itemId) {
        return orderService.getOrderItemById(itemId);
    }
}
