package org.globaroman.petshopba.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.AddressMapper;
import org.globaroman.petshopba.mapper.OrderItemMapper;
import org.globaroman.petshopba.mapper.OrderMapper;
import org.globaroman.petshopba.model.cartorder.Address;
import org.globaroman.petshopba.model.cartorder.Order;
import org.globaroman.petshopba.model.cartorder.OrderItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.AddressRepository;
import org.globaroman.petshopba.repository.OrderItemRepository;
import org.globaroman.petshopba.repository.OrderRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.globaroman.petshopba.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper itemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ResponseOrderDto addOrder(
            CreateOrderRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Address address = addressMapper.toEntity(requestDto);
        Address savedAddress = addressRepository.save(address);
        Order order = getOrderFromShoppingCart(user, savedAddress);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<ResponseOrderDto> getAllOrder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public ResponseOrderDto updateStatusToOrder(OrderStatusDto statusDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can't find order with id: " + id));
        order.setStatus(statusDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<ResponseOrderItemDto> getOrderItensFromOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can't find order with id: " + orderId));
        return orderMapper.orderItemsToDtos(order.getOrderItems());
    }

    @Override
    public ResponseOrderItemDto getOrderItemById(Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find orderItem with id: " + itemId)
        );
        return itemMapper.toDto(orderItem);
    }

    private Order getOrderFromShoppingCart(User user, Address address) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Can't find shopping cart for user with id: " + user.getId()));
        Order order = getOrderWithFields(shoppingCart, address);

        shoppingCartRepository.delete(shoppingCart);
        return order;
    }

    private Order getOrderWithFields(
            ShoppingCart shoppingCart,
            Address address) {

        Order order = orderMapper.shoppingCartToOrder(shoppingCart);
        order.setAddress(address);
        Order orderSave = orderRepository.save(order);

        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(itemMapper::cartItemToOrderItem)
                .peek(o -> o.setOrder(orderSave))
                .map(this::addItemToDb)
                .collect(Collectors.toSet());

        orderSave.setTotal(getTotalCostOrder(orderItems));
        orderSave.setOrderItems(orderItems);
        return orderSave;
    }

    private OrderItem addItemToDb(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    private BigDecimal getTotalCostOrder(Set<OrderItem> orderItems) {
        double sum = orderItems.stream()
                .mapToDouble(o ->
                        o.getPrice().doubleValue() * o.getQuantity())
                .sum();
        return new BigDecimal(sum);
    }
}
