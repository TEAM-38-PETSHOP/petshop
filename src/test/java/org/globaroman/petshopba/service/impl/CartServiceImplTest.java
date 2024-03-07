package org.globaroman.petshopba.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.globaroman.petshopba.dto.ordercart.CartItemRequestDto;
import org.globaroman.petshopba.dto.ordercart.ShoppingCartResponseDto;
import org.globaroman.petshopba.mapper.CartItemMapper;
import org.globaroman.petshopba.mapper.ShoppingCartMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.cartorder.CartItem;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.CartItemRepository;
import org.globaroman.petshopba.repository.ProductRepository;
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
class CartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private ShoppingCart shoppingCart;

    private Authentication authentication;

    @BeforeEach
    void setUp() {

        user = new User()
                .setId(1L)
                .setEmail("roman@gmail.com")
                .setPassword("$2a$10$2P9C9iZmpeNBNt2qrNKHcO7mxE/DcDV62TVvHa1OZpa1Ha3Hzi1Va")
                .setFirstName("John")
                .setLastName("Duo");

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);

        authentication = new org.springframework.security
                .authentication.UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    @DisplayName("Create a new ShoppingCart if its not exist or add CartItem -> "
            + "return ResponseDto and Status Created")
    void addProduct_ShouldCreateNewShoppingCartOrAndAddCartItem_ResultOk() {
        CartItem cartItem = getCartItem();
        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemSet.add(cartItem);
        shoppingCart.setCartItems(cartItemSet);
        Product product = getProduct();

        Mockito.when(shoppingCartRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(productRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(product));
        Mockito.when(cartItemRepository.save(Mockito.any(CartItem.class)))
                .thenReturn(cartItem);
        Mockito.when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class)))
                .thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toShoppingCartDto(shoppingCart))
                .thenReturn(new ShoppingCartResponseDto());

        CartItemRequestDto requestDto = getCartItemRequestDto();

        ShoppingCartResponseDto result = cartService.addProduct(requestDto, authentication);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Update exist CartItem by Id -> return ResponseDto and Status ok")
    void update_ShouldUpdateExistCartItem_ResultOk() {
        CartItem cartItem = getCartItem();
        CartItem updateCartItem = getCartItem();
        updateCartItem.setQuantity(15);
        CartItemRequestDto cartItemRequestDto = getCartItemRequestDto();
        cartItemRequestDto.setQuantity(15);

        Mockito.when(cartItemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(cartItem));
        Mockito.when(cartItemMapper.toUpdate(cartItemRequestDto, cartItem))
                .thenReturn(updateCartItem);
        Mockito.when(cartItemRepository.save(updateCartItem))
                .thenReturn(updateCartItem);
        Mockito.when(shoppingCartRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(shoppingCartMapper.toShoppingCartDto(shoppingCart))
                .thenReturn(new ShoppingCartResponseDto());

        ShoppingCartResponseDto result = cartService.update(1L, cartItemRequestDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(cartItemRequestDto.getQuantity(), updateCartItem.getQuantity());
    }

    @Test
    @DisplayName("Delete exist CartItem -> return Status ok")
    void deleteById_ShouldDeliteExistCartItemById_ResultOk() {

        cartService.deleteById(1L);
        Mockito.verify(cartItemRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Get exist ShoppingCart -> return ResponseDto SC and Status ok")
    void getShoppingCart_ShouldReturnOneShoppingCartByUser_ResultOk() {

        Mockito.when(shoppingCartRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(shoppingCartMapper.toShoppingCartDto(shoppingCart))
                .thenReturn(new ShoppingCartResponseDto());

        ShoppingCartResponseDto result = cartService.getShoppingCart(authentication);

        Assertions.assertNotNull(result);
    }

    private CartItem getCartItem() {
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(10);
        cartItem.setProduct(getProduct());
        cartItem.setId(1L);

        return cartItem;
    }

    private CartItemRequestDto getCartItemRequestDto() {
        CartItemRequestDto requestDto = new CartItemRequestDto();
        requestDto.setQuantity(10);
        requestDto.setProductId(1L);
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
        product.setImage("image.jpeg");
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
