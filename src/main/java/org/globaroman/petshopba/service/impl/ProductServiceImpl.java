package org.globaroman.petshopba.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.CountParameterDto;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.PriceSearchParameter;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.globaroman.petshopba.dto.product.RequestUpdateImageToProductDto;
import org.globaroman.petshopba.dto.product.SimpleSearchProductParameter;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.ProductMapper;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.repository.CategoryRepository;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.repository.specification.product.ProductSpecificationBuilder;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.globaroman.petshopba.service.ProductService;
import org.globaroman.petshopba.service.TransliterationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AmazonS3Service amazonS3Service;
    private final UploadImageServiceImpl upload;
    private final ProductSpecificationBuilder productSpecificationBuilder;
    private final TransliterationService transliterationService;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto create(CreateRequestProductDto requestProductDto) {
        Product product = productMapper.toModel(requestProductDto);
        List<String> urlsImageForModel = new ArrayList<>();

        List<String> imageUrls = requestProductDto.getImageUrls();

        for (String url : imageUrls) {

            urlsImageForModel.add(amazonS3Service.uploadImageUrl(
                    upload.downloadImage(url),
                    getStringObjectKey(
                            requestProductDto.getBrand(),
                            requestProductDto.getName())));
        }
        product.setImageUrls(urlsImageForModel);
        product.setProductNameId(
                transliterationService.getLatinStringLine(product.getName()));

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto getById(Long id) {
        Product product = getProductFromDb(id);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProductsByCategoryId(Long id, Pageable pageable) {
        return productRepository.findAllByCategories_Id(id, pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> getAllProductsByAnimalId(Long id, Pageable pageable) {
        return productRepository.findAllByAnimals_Id(id, pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> search(ProductSearchParameters params, Pageable pageable) {
        Specification<Product> productSpecification = productSpecificationBuilder.build(params);
        return productRepository.findAll(productSpecification, pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public CountParameterDto searchAllCount(ProductSearchParameters params) {
        Specification<Product> productSpecification = productSpecificationBuilder.build(params);
        CountParameterDto countParameterDto = new CountParameterDto(productRepository
                .findAll(productSpecification)
                .stream()
                .count());

        return countParameterDto;
    }

    @Override
    public List<ProductResponseDto> searchByName(SimpleSearchProductParameter productParameter,
                                                 Pageable pageable) {
        return productRepository.findByName(productParameter.parameter(), pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public CountParameterDto searchByNameCount(SimpleSearchProductParameter productParameter) {

        return new CountParameterDto(productRepository.countByName(productParameter.parameter()));
    }

    @Override
    public List<ProductResponseDto> searchByBrand(SimpleSearchProductParameter productParameter,
                                                  Pageable pageable) {
        return productRepository.findByBrand(productParameter.parameter(), pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public CountParameterDto searchByBrandCount(SimpleSearchProductParameter productParameter) {
        return new CountParameterDto(productRepository.countByBrand(productParameter.parameter()));
    }

    @Override
    public List<ProductResponseDto> searchByPrice(PriceSearchParameter priceSearchParameter,
                                                  Pageable pageable) {
        String fromPrice = "0";
        String toPrice = "10000";
        if (!priceSearchParameter.from().isBlank() && !priceSearchParameter.to().isBlank()) {
            fromPrice = priceSearchParameter.from();
            toPrice = priceSearchParameter.to();
        }

        return productRepository.findByPriceFromTo(fromPrice, toPrice, pageable).stream()
                .sorted(getComparatorForPrice())
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public CountParameterDto searchByPriceCount(PriceSearchParameter priceSearchParameter) {
        return new CountParameterDto(productRepository
                .countPriceAllProducts(priceSearchParameter.from(),
                priceSearchParameter.to()));
    }

    @Override
    public List<ProductResponseDto> searchByAnimalAndPrice(Long animalId,
                                                           PriceSearchParameter parameter,
                                                           Pageable pageable) {
        String fromPrice = "0";
        String toPrice = "10000";
        if (!parameter.from().isBlank() && !parameter.to().isBlank()) {
            fromPrice = parameter.from();
            toPrice = parameter.to();
        }

        return productRepository.findByPriceByAnimalFromTo(animalId, fromPrice, toPrice).stream()
                .sorted(getComparatorForPrice())
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public CountParameterDto searchByAnimalAndPriceCount(Long animalId,
                                                         PriceSearchParameter parameter) {
        return new CountParameterDto(productRepository.countByAnimalAndPrice(animalId,
                parameter.from(),
                parameter.to()));
    }

    @Override
    public List<ProductResponseDto> searchByCategoryAndPrice(Long categoryId,
                                                             PriceSearchParameter parameter,
                                                             Pageable pageable) {
        String fromPrice = "0";
        String toPrice = "10000";
        if (!parameter.from().isBlank() && !parameter.to().isBlank()) {
            fromPrice = parameter.from();
            toPrice = parameter.to();
        }

        return productRepository.findByPriceByCategoryFromTo(categoryId, fromPrice, toPrice)
                .stream()
                .sorted(getComparatorForPrice())
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public CountParameterDto searchByCategoryAndPriceCount(Long categoryId,
                                                           PriceSearchParameter parameter) {
        return new CountParameterDto(productRepository.countCategoryAndPrice(categoryId,
                parameter.from(),
                parameter.to()));
    }

    @Override
    public List<ProductResponseDto> getRandomProducts(int count) {
        return productRepository.findRandomProducts(count).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> getAllProductsByAnimalAndCategory(
            Long animalId,
            Long categoryId,
            Pageable pageable) {
        return productRepository.findAllByAnimalsAndCategories(animalId, categoryId, pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto updateImageToProduct(Long id,
                                                   RequestUpdateImageToProductDto requestImageDto) {
        Product productFromDb = getProductFromDb(id);

        List<String> urlsImageForModel = productFromDb.getImageUrls();

        List<String> imageUrls = requestImageDto.getImageUrls();

        for (String url : imageUrls) {
            urlsImageForModel.add(amazonS3Service.uploadImageUrl(
                    upload.downloadImage(url),
                    getStringObjectKey(productFromDb.getBrand(), productFromDb.getName())));
        }
        productFromDb.setImageUrls(urlsImageForModel);

        return productMapper.toDto(productRepository.save(productFromDb));

    }

    @Override
    public CountParameterDto countAllProducts() {
        CountParameterDto countParameterDto =
                new CountParameterDto(productRepository.countAllProducts());
        return countParameterDto;
    }

    @Override
    public CountParameterDto countProductsByCategoryId(Long id) {

        return new CountParameterDto(productRepository.countProductsByCategoriesId(id));
    }

    @Override
    public CountParameterDto countProductsByAnimalId(Long id) {
        return new CountParameterDto(productRepository.countProductsByAnimalsId(id));
    }

    @Override
    public CountParameterDto countProductsByAnimalAndCategory(Long animalId, Long categoryId) {
        return new CountParameterDto(productRepository
                .countProductsByAnimalsIdAndCategoriesId(animalId, categoryId));
    }

    @Override
    public void updateNameId() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            product.setProductNameId(transliterationService.getLatinStringLine(product.getName()));
        }

        productRepository.saveAll(products);

        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            category.setCategoryNameId(transliterationService
                    .getLatinStringLine(category.getName()));
        }

        categoryRepository.saveAll(categories);
    }

    @Override
    public ProductResponseDto update(Long id,
                                     CreateRequestProductDto requestProductDto) {
        Product product = getProductFromDb(id);
        Product updateProduct = productMapper.toUpdate(requestProductDto, product);
        updateProduct.setProductNameId(
                transliterationService.getLatinStringLine(updateProduct.getName()));
        return productMapper.toDto(productRepository.save(updateProduct));
    }

    @Override
    public boolean delete(Long id) {
        return productRepository.findById(id).map(
                product -> {
                    product.getImageUrls()
                            .forEach(amazonS3Service::deleteImage);
                    productRepository.deleteById(id);
                    return true;
                }
        ).orElse(false);
    }

    private Product getProductFromDb(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("can not find product with id: " + id)
        );
    }

    private String getStringObjectKey(String brand, String nameProduct) {
        Random random = new Random();
        int nameImage = random.nextInt(10000000);

        String latinLine = transliterationService.getLatinStringLine(nameProduct);

        return brand + "_" + getNameProductShort(latinLine) + "_" + nameImage + ".jpg";

    }

    private String getNameProductShort(String name) {
        String[] splStr = name.split(" ");
        if (splStr.length > 3) {
            return splStr[0] + " " + splStr[1] + " " + splStr[2];
        } else {
            return name;
        }
    }

    private Comparator<Product> getComparatorForPrice() {
        return (o1, o2) -> o1.getPrice().compareTo(o2.getPrice());
    }
}
