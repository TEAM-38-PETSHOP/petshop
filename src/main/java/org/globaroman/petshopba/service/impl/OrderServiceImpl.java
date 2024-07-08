package org.globaroman.petshopba.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.dto.ordercart.CreateOrderNoNameRequestDto;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.PeriodDataParameterDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.AddressMapper;
import org.globaroman.petshopba.mapper.OrderItemMapper;
import org.globaroman.petshopba.mapper.OrderMapper;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.cartorder.Address;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.globaroman.petshopba.model.cartorder.Order;
import org.globaroman.petshopba.model.cartorder.OrderItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.globaroman.petshopba.model.cartorder.Status;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.model.user.UserTemp;
import org.globaroman.petshopba.repository.AddressRepository;
import org.globaroman.petshopba.repository.OrderItemRepository;
import org.globaroman.petshopba.repository.OrderRepository;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.globaroman.petshopba.repository.UserTempRepository;
import org.globaroman.petshopba.service.EmailSenderService;
import org.globaroman.petshopba.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmailSenderService emailSenderService;
    private final UserTempRepository userTempRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ResponseOrderDto addOrder(
            CreateOrderRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Address address = addressMapper.toEntity(requestDto);
        Address savedAddress = addressRepository.save(address);
        Order order = getOrderFromShoppingCart(user, savedAddress);

        sendMassageToUserAboutOrder(order);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public ResponseOrderDto addOrderNoName(CreateOrderNoNameRequestDto requestDto) {
        UserTemp user = createNewUserTemplate(requestDto);
        Address address = addressMapper.toModelTemp(requestDto.getAddress());
        Address savedAddress = addressRepository.save(address);
        List<CartItem> cartItems = getCartItemsFromDto(requestDto);
        Order order = getOrderForTemplateUsers(user, savedAddress, cartItems);
        sendMassageToUserAboutOrder(order);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<ResponseOrderDto> getAllOrderForAdmin(PeriodDataParameterDto parameterDto) {
        return null;
    }

    @Override
    public List<ResponseOrderDto> getAllOrderByUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public ResponseOrderDto updateStatusToOrder(OrderStatusDto statusDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Can't find order with id: " + id);
                    return new EntityNotFoundCustomException("Can't find order with id: " + id);
                });
        order.setStatus(statusDto.getStatus());

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<ResponseOrderItemDto> getOrderItensFromOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> {
                    log.error("Can't find order with id: " + orderId);
                    return new EntityNotFoundCustomException(
                            "Can't find order with id: " + orderId);
                });
        return orderMapper.orderItemsToDtos(order.getOrderItems());
    }

    @Override
    public ResponseOrderItemDto getOrderItemById(Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElseThrow(
                () -> {
                    log.error("Can not find orderItem with id: " + itemId);
                    return new EntityNotFoundCustomException(
                            "Can not find orderItem with id: " + itemId);
                }
        );
        return orderItemMapper.toDto(orderItem);
    }

    private void sendMassageToUserAboutOrder(Order order) {
        String email = "";
        if (order.getUser() == null) {
            email = order.getUserTemp().getEmail();
        } else {
            email = order.getUser().getEmail();
        }

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        emailSenderService.sendEmail(
                "OneGroom.com.ua",
                email,
                "Замовлення № " + order.getId(),
                "Ваше замовлення №" + order.getId() + " прийняте.\n"
                        + "Ви можете відстежити статус свого замовлення в особистому кабінеті.\n"
                        + "Замовлення від " + order.getOrderDate().format(formatDate) + "\n"
                        + getOrderItemTextingName(order.getOrderItems()) + "\n"
                        + "Загальна сума замовлення: " + order.getTotal() + " грн."
                        + "\n\nВаш коментар: \"" + order.getAddress().getComment() + " \""
        );
    }

    private List<CartItem> getCartItemsFromDto(CreateOrderNoNameRequestDto requestDto) {
        return requestDto.getCartItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> {
                                log.error("Can not find product with id: " + item.getProductId());
                                throw new EntityNotFoundCustomException(
                                        "Can not find product with id: "
                                        + item.getProductId());
                            });

                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(item.getQuantity());
                    cartItem.setProduct(product);
                    return cartItem;
                })
                .collect(Collectors.toList());
    }

    private Order getOrderForTemplateUsers(UserTemp user,
                                           Address address,
                                           List<CartItem> cartItems) {
        Order order = new Order();
        order.setUserTemp(user);
        order.setOrderDate(LocalDateTime.now());
        order.setAddress(address);
        order.setStatus(Status.PENDING);
        order.setTotal(BigDecimal.valueOf(0.00));
        Order savedOrder = orderRepository.save(order);
        Set<OrderItem> orderItems = cartItems.stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .peek(o -> o.setOrder(savedOrder))
                .map(this::addItemToDb)
                .collect(Collectors.toSet());

        savedOrder.setTotal(getTotalCostOrder(orderItems));
        savedOrder.setOrderItems(orderItems);
        savedOrder.setStatus(Status.PENDING);

        return savedOrder;
    }

    private UserTemp createNewUserTemplate(CreateOrderNoNameRequestDto requestDto) {
        UserTemp user = new UserTemp();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPhone(requestDto.getPhone());
        return userTempRepository.save(user);
    }

    private Order getOrderFromShoppingCart(User user, Address address) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> {
                            log.error("Can't find shopping cart for user with id: " + user.getId());
                            return new EntityNotFoundCustomException(
                                    "Can't find shopping cart for user with id: " + user.getId());
                        });
        if (shoppingCart.getCartItems().isEmpty()) {
            log.error("Can not create order because of cartItem is empty");
            throw new RuntimeException("Can not create order because of cartItem is empty");
        }
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
                .map(orderItemMapper::cartItemToOrderItem)
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
        return new BigDecimal(sum).setScale(2, RoundingMode.HALF_EVEN);
    }

    private String getOrderItemTextingName(Set<OrderItem> orderItems) {
        StringBuilder sb = new StringBuilder();
        for (OrderItem orderItem : orderItems) {
            sb.append("Артикль №").append(orderItem.getProduct().getId()).append("\n")
                    .append(orderItem.getProduct().getName()).append("\n")
                    .append("Кількість: ").append(orderItem.getQuantity()).append(" шт.\n")
                    .append("Ціна: ").append(orderItem.getPrice()
                            .setScale(2, RoundingMode.HALF_EVEN))
                    .append("\n");
        }
        return sb.toString();
    }
}
