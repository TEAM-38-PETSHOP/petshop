package org.globaroman.petshopba.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.OrderStatusDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderDto;
import org.globaroman.petshopba.dto.ordercart.ResponseOrderItemDto;
import org.globaroman.petshopba.mapper.AddressMapper;
import org.globaroman.petshopba.mapper.OrderItemMapper;
import org.globaroman.petshopba.mapper.OrderMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.cartorder.Address;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.globaroman.petshopba.model.cartorder.Order;
import org.globaroman.petshopba.model.cartorder.OrderItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.globaroman.petshopba.model.cartorder.Status;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.AddressRepository;
import org.globaroman.petshopba.repository.OrderItemRepository;
import org.globaroman.petshopba.repository.OrderRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private EmailSenderServiceImpl emailSenderService;

    private User user;
    private ShoppingCart shoppingCart;

    private Authentication authentication;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setEmail("roman@gmail.com");
        user.setPassword("$2a$10$2P9C9iZmpeNBNt2qrNKHcO7mxE/DcDV62TVvHa1OZpa1Ha3Hzi1Va");
        user.setFirstName("John");
        user.setLastName("Duo");

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(Set.of(getCartItem()));

        authentication = new org.springframework.security
                .authentication.UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    @DisplayName("Create new Order -> return ResponseDto and Status Created")
    void addOrder_ShouldCreateNewOrder_ResultOk() {
        Address address = getAddress();
        Order order = getOrder();

        Mockito.when(addressMapper.toEntity(Mockito.any(CreateOrderRequestDto.class)))
                .thenReturn(address);
        Mockito.when(addressRepository.save(address))
                .thenReturn(address);
        Mockito.when(shoppingCartRepository.findByUserId(1L))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(orderMapper.shoppingCartToOrder(shoppingCart))
                .thenReturn(order);
        Mockito.when(orderRepository.save(order))
                .thenReturn(order);
        Mockito.when(orderItemMapper.cartItemToOrderItem(getCartItem()))
                .thenReturn(getOrderItem());
        Mockito.when(orderItemRepository.save(getOrderItem()))
                .thenReturn(getOrderItem());
        Mockito.doNothing().when(shoppingCartRepository)
                .delete(Mockito.any(ShoppingCart.class));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        String dayToday = order.getOrderDate().format(formatter);

        Mockito.when(emailSenderService.sendEmail(
                "OneGroom.com.ua",
                "roman@gmail.com",
                "Замовлення № 1",
                "Ваше замовлення №1 прийняте.\n"
                        + "Ви можете відстежити статус свого замовлення в особистому кабінеті.\n"
                        + "Замовлення від " + dayToday + "\n"
                        + "Артикль №1\n"
                        + "Product\n"
                        + "Кількість: 1 шт.\n"
                        + "Ціна: 100.00\n"
                        + "\n"
                        + "Загальна сума замовлення: 100.00 грн."))
                .thenReturn("Email sent successfully!");
        Mockito.when(orderRepository.save(order))
                .thenReturn(order);
        Mockito.when(orderMapper.toDto(order))
                .thenReturn(getResponseOrderDto());

        CreateOrderRequestDto requestDto = getRequestDto();
        ResponseOrderDto result = orderService.addOrder(requestDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestDto.getCity(),
                getResponseOrderDto().getAddress().getCity());
    }

    @Test
    @DisplayName("Get all Orders with OrderItem by User-> return ResponseDto and Status ok")
    void getAllOrder_ShouldGetAllOrderByUser_ResultOk() {
        Order order = getOrder();
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        Mockito.when(orderRepository.findAllByUserId(Mockito.anyLong())).thenReturn(orders);
        Mockito.when(orderMapper.toDto(order)).thenReturn(getResponseOrderDto());

        List<ResponseOrderDto> result = orderService.getAllOrderByUser(authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Update status of exist Order by id -> return ResponseDto and Status ok")
    void updateStatusToOrder_ShouldUpdateStatusExistOrder_ResultOk() {
        OrderStatusDto orderStatusDto = new OrderStatusDto();
        orderStatusDto.setStatus(Status.COMPLETED);
        Order order = getOrder();
        Order updateOrder = getOrder();
        updateOrder.setStatus(Status.COMPLETED);
        ResponseOrderDto responseOrderDto = getResponseOrderDto();
        responseOrderDto.setStatus(Status.COMPLETED);

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(updateOrder);

        Mockito.when(orderMapper.toDto(updateOrder)).thenReturn(responseOrderDto);

        ResponseOrderDto result = orderService.updateStatusToOrder(orderStatusDto, 1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateOrder.getStatus(), responseOrderDto.getStatus());

    }

    @Test
    @DisplayName("Get all OrderItems by Order -> return ResponseDto and Status ok")
    void getOrderItensFromOrder_ShouldReturnAllOrderItemsByOrder_ResultOk() {

        Order order = getOrder();
        OrderItem orderItem = getOrderItem();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(order));
        Mockito.when(orderMapper.orderItemsToDtos(order.getOrderItems()))
                .thenReturn(List.of(new ResponseOrderItemDto()));

        List<ResponseOrderItemDto> result = orderService.getOrderItensFromOrder(1L);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Get OrderItem by Id -> return ResponseDto and Status ok")
    void getOrderItemById_ShouldReturnOrderItemById_ResultOk() {
        OrderItem orderItem = getOrderItem();

        Mockito.when(orderItemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(orderItem));
        Mockito.when(orderItemMapper.toDto(orderItem))
                .thenReturn(new ResponseOrderItemDto());

        ResponseOrderItemDto result = orderService.getOrderItemById(1L);

        Assertions.assertNotNull(result);
    }

    private Order getOrder() {
        Order order = new Order();
        order.setAddress(getAddress());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setId(1L);
        order.setUser(user);
        order.setTotal(BigDecimal.valueOf(100L));
        Set<OrderItem> sets = new HashSet<>();
        sets.add(getOrderItem());
        order.setOrderItems(sets);

        return order;
    }

    private OrderItem getOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(1);
        orderItem.setProduct(getProduct());
        orderItem.setPrice(BigDecimal.valueOf(100L));
        return orderItem;
    }

    private ResponseOrderDto getResponseOrderDto() {
        ResponseOrderDto responseOrderDto = new ResponseOrderDto();
        responseOrderDto.setOrderDate(LocalDateTime.now());

        responseOrderDto.setOrderItems(Set.of(new ResponseOrderItemDto()));
        responseOrderDto.setId(1L);
        responseOrderDto.setTotal(BigDecimal.valueOf(100L));
        responseOrderDto.setStatus(Status.PENDING);
        responseOrderDto.setAddress(getAddress());
        responseOrderDto.setUserId(user.getId());

        return responseOrderDto;
    }

    private CartItem getCartItem() {
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(1);
        cartItem.setProduct(getProduct());
        cartItem.setId(1L);

        return cartItem;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setId(1L);
        address.setApartment("100");
        address.setBuilding("15");
        address.setCity("Kyiv");
        address.setStreet("Shevchenko");
        address.setOfficeNovaPost("");
        return address;
    }

    private CreateOrderRequestDto getRequestDto() {
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto();
        requestDto.setApartment("100");
        requestDto.setBuilding("15");
        requestDto.setCity("Kyiv");
        requestDto.setStreet("Shevchenko");
        requestDto.setOfficeNovaPost("");
        return requestDto;
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        Animal responseAnimalDto = new Animal();
        product.setId(1L);
        Animal responseAnimalDto2 = new Animal();
        product.setId(2L);
        product.setAnimals(List.of(responseAnimalDto, responseAnimalDto2));
        product.setBrand("Brand");
        product.setImageUrls(List.of("image.jpeg"));
        product.setName("Product");
        product.setDescription("This is description of product");
        product.setId(1L);
        Category responseCategoryDto = new Category();
        responseCategoryDto.setId(1L);
        Category responseCategoryDto2 = new Category();
        responseCategoryDto.setId(2L);
        product.setCategories(List.of(responseCategoryDto, responseCategoryDto2));
        product.setPrice(BigDecimal.valueOf(15.99));
        product.setId(1L);

        return product;
    }
}
