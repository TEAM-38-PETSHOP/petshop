package org.globaroman.petshopba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.dto.category.CreateRequestCategoryDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;
import org.globaroman.petshopba.mapper.CategoryMapper;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.repository.CategoryRepository;
import org.globaroman.petshopba.service.TransliterationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private TransliterationService transliterationService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Create a category -> get a ResponseDto like successful result")
    void create_Category_ShouldCreateNewCategoryAndReturnResponseDto() {
        CreateRequestCategoryDto requestCategoryDto = getRequestDto();
        Category category = getCategory();
        ResponseCategoryDto responseCategoryDto = getResponseDto();

        Mockito.when(categoryMapper.toModel(requestCategoryDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(responseCategoryDto);
        Mockito.when(transliterationService.getLatinStringLine("Category"))
                .thenReturn(Mockito.anyString());

        ResponseCategoryDto result = categoryService.create(requestCategoryDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getCategoryId());
    }

    @Test
    @DisplayName("Get all category -> get List<ResponseDto> like successful result")
    void getAll_Category_ShouldReturnListWithAllResponseDto() {
        Category category = getCategory();
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(categoryMapper.toDto(Mockito.any(Category.class)))
                .thenReturn(new ResponseCategoryDto());

        List<ResponseCategoryDto> result = categoryService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get category by its id -> get exist category successful result")
    void getById_Category_ShouldReturnExistCategoryAsResponseDto() {
        Category category = getCategory();
        ResponseCategoryDto responseCategoryDto = getResponseDto();

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(responseCategoryDto);

        ResponseCategoryDto result = categoryService.getById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getCategoryId());
    }

    @Test
    @DisplayName("Update the exist category -> Update and get ResponseDto as successful result")
    void update_Category_ShouldUpdateExistCategoryAndReturnResponseDto() {
        CreateRequestCategoryDto requestCategoryDto = getRequestDto();
        requestCategoryDto.setName("New name");
        Category category = getCategory();
        Category updateCategory = getCategory();
        updateCategory.setName("New name");
        ResponseCategoryDto responseCategoryDto = getResponseDto();
        responseCategoryDto.setName("New name");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toUpdate(requestCategoryDto, category))
                .thenReturn(updateCategory);
        Mockito.when(categoryRepository.save(updateCategory)).thenReturn(updateCategory);
        Mockito.when(categoryMapper.toDto(updateCategory)).thenReturn(responseCategoryDto);
        Mockito.when(transliterationService.getLatinStringLine("New name"))
                .thenReturn(Mockito.anyString());

        ResponseCategoryDto result = categoryService.update(1L, requestCategoryDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getCategoryId());
        Assertions.assertEquals(requestCategoryDto.getName(), result.getName());
    }

    @Test
    @DisplayName("Delete the exist category -> get successful result")
    void delete_Category_ShouldDeleteExistCategoryAsResponseDto() {
        Category category = getCategory();

        categoryService.delete(category.getId());

        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(1L);
    }

    private CreateRequestCategoryDto getRequestDto() {
        CreateRequestCategoryDto requestCategoryDto = new CreateRequestCategoryDto();
        requestCategoryDto.setName("Category");
        requestCategoryDto.setDescription("This is a description a category of product");
        return requestCategoryDto;
    }

    private ResponseCategoryDto getResponseDto() {
        ResponseCategoryDto responseCategoryDto = new ResponseCategoryDto();
        responseCategoryDto.setCategoryId(1L);
        responseCategoryDto.setName("Category");
        responseCategoryDto.setDescription("This is a description a category of product");
        return responseCategoryDto;
    }

    private Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category");
        category.setDescription("This is a description a category of product");
        return category;
    }
}
