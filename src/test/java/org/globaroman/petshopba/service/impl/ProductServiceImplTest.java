package org.globaroman.petshopba.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.mapper.ProductMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private AmazonS3Service amazonS3Service;

    @Mock
    private UploadImageServiceImpl upload;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Create a product -> get a ResponseDto like successful result")
    void create_Product_ShouldCreateNewProductAndReturnResponseDto() {
        CreateRequestProductDto requestProductDto = getRequestDto();
        Product product = getProduct();
        byte[] data = "https://url/image.jpg".getBytes();
        InputStream inputStream = new ByteArrayInputStream(data);

        Mockito.when(upload.downloadImage(Mockito.anyString())).thenReturn(inputStream);
        Mockito.when(productMapper.toModel(requestProductDto)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto responseDto = getResponseDto();
        Mockito.when(productMapper.toDto(product)).thenReturn(responseDto);
        Mockito.when(amazonS3Service.uploadImageUrl(Mockito.any(InputStream.class),
                Mockito.anyString())).thenReturn(Mockito.anyString());

        ProductResponseDto result = productService.create(requestProductDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getProductId());
        Assertions.assertEquals(result.getAnimals().size(),
                requestProductDto.getAnimalsId().size());
    }

    @Test
    @DisplayName("Get all products -> get List<ResponseDto> like successful result")
    void getAll_Product_ShouldReturnListWithAllResponseDto() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = new ArrayList<>();
        Product product = getProduct();
        products.add(product);
        Page<Product> mockPage = new PageImpl<>(products);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(mockPage);
        Mockito.when(productMapper.toDto(Mockito.any(Product.class)))
                .thenReturn(new ProductResponseDto());

        List<ProductResponseDto> result = productService.getAll(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get product by its id -> get exist category successful result")
    void getById_Product_ShouldReturnExistProductAsResponseDto() {
        Product product = getProduct();
        ProductResponseDto responseDto = getResponseDto();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productMapper.toDto(product)).thenReturn(responseDto);

        ProductResponseDto result = productService.getById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getProductId());
    }

    @Test
    @DisplayName("Get all products by category Id -> get List<ResponseDto> like successful result")
    void getAllProductsByCategoryId_Product_ShouldReturnListWithAllResponseDtoByExistCategory() {
        Pageable pageable = Pageable.unpaged();
        Product product = getProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);

        Mockito.when(productRepository.findAllByCategories_Id(Mockito.anyLong(),
                        Mockito.eq(pageable))).thenReturn(products);
        Mockito.when(productMapper.toDto(Mockito.any(Product.class)))
                .thenReturn(new ProductResponseDto());

        List<ProductResponseDto> result = productService.getAllProductsByCategoryId(1L, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get all products by category Id -> get List<ResponseDto> like successful result")
    void getAllProductsByCategoryId_Product_ShouldReturnListWithAllResponseDtoByExistAnimal() {
        Pageable pageable = Pageable.unpaged();
        Product product = getProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);

        Mockito.when(productRepository.findAllByAnimals_Id(Mockito.anyLong(), Mockito.eq(pageable)))
                .thenReturn(products);
        Mockito.when(productMapper.toDto(Mockito.any(Product.class)))
                .thenReturn(new ProductResponseDto());

        List<ProductResponseDto> result = productService.getAllProductsByAnimalId(1L, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Update the exist product -> Update and get ResponseDto as successful result")
    void update_Product_ShouldUpdateExistProductAndReturnResponseDto() {
        CreateRequestProductDto requestProductDto = getRequestDto();
        requestProductDto.setBrand("UpdateBrand");
        Product product = getProduct();
        ProductResponseDto responseDto = getResponseDto();
        responseDto.setBrand("UpdateBrand");
        Product updateProduct = getProduct();
        updateProduct.setBrand("UpdateBrand");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productMapper.toUpdate(requestProductDto, product)).thenReturn(updateProduct);
        Mockito.when(productRepository.save(updateProduct)).thenReturn(updateProduct);
        Mockito.when(productMapper.toDto(updateProduct)).thenReturn(responseDto);

        ProductResponseDto result = productService.update(1L, requestProductDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getProductId());
        Assertions.assertEquals(requestProductDto.getBrand(), result.getBrand());
    }

    @Test
    @DisplayName("Delete the exist product -> get successful result")
    void delete_Product_ShouldDeleteExistProductAsResponseDto() {
        Product product = getProduct();
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(amazonS3Service.deleteImage(Mockito.anyString()))
                .thenReturn(Mockito.anyString());
        productService.delete(product.getId());

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(product.getId());
    }

    private CreateRequestProductDto getRequestDto() {
        CreateRequestProductDto requestProductDto = new CreateRequestProductDto();
        List<Long> animalIds = new ArrayList<>();
        animalIds.add(1L);
        animalIds.add(2L);
        requestProductDto.setAnimalsId(animalIds);
        requestProductDto.setName("Product");
        requestProductDto.setBrand("Brand");
        requestProductDto.setImage("image.jpeg");
        requestProductDto.setPrice(BigDecimal.valueOf(15.99));
        requestProductDto.setCategoriesId(animalIds);
        requestProductDto.setDescription("This is description of product");

        return requestProductDto;
    }

    private ProductResponseDto getResponseDto() {
        ProductResponseDto responseDto = new ProductResponseDto();
        ResponseAnimalDto responseAnimalDto = new ResponseAnimalDto();
        responseAnimalDto.setAnimalId(1L);
        ResponseAnimalDto responseAnimalDto2 = new ResponseAnimalDto();
        responseAnimalDto2.setAnimalId(2L);
        responseDto.setAnimals(List.of(responseAnimalDto, responseAnimalDto2));
        responseDto.setBrand("Brand");
        responseDto.setImage("image.jpeg");
        responseDto.setName("Product");
        responseDto.setDescription("This is description of product");
        responseDto.setProductId(1L);
        ResponseCategoryDto responseCategoryDto = new ResponseCategoryDto();
        responseCategoryDto.setCategoryId(1L);
        ResponseCategoryDto responseCategoryDto2 = new ResponseCategoryDto();
        responseCategoryDto2.setCategoryId(2L);
        responseDto.setCategories(List.of(responseCategoryDto, responseCategoryDto2));
        responseDto.setPrice(BigDecimal.valueOf(15.99));
        responseDto.setProductId(1L);

        return responseDto;
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
