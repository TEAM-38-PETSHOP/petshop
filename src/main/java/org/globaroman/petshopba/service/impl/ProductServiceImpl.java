package org.globaroman.petshopba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.CountParameterDto;
import org.globaroman.petshopba.dto.product.CreateRequestProductDto;
import org.globaroman.petshopba.dto.product.ProductResponseDto;
import org.globaroman.petshopba.dto.product.ProductSearchParameters;
import org.globaroman.petshopba.dto.product.RequestUpdateImageToProductDto;
import org.globaroman.petshopba.dto.product.SimpleSearchProductParameter;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.ProductMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.Category;
import org.globaroman.petshopba.model.Product;
import org.globaroman.petshopba.model.groom.PetService;
import org.globaroman.petshopba.model.groom.TypePetService;
import org.globaroman.petshopba.repository.AnimalRepository;
import org.globaroman.petshopba.repository.CategoryRepository;
import org.globaroman.petshopba.repository.PetServiceRepository;
import org.globaroman.petshopba.repository.ProductRepository;
import org.globaroman.petshopba.repository.TypePetServiceRepository;
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

    //TODO delete method updateNAmeId
    private final AnimalRepository animalRepository;
    private final CategoryRepository categoryRepository;
    private final TypePetServiceRepository typePetServiceRepository;
    private final PetServiceRepository petServiceRepository;

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
    public List<ProductResponseDto> searchByName(SimpleSearchProductParameter productParameter,
                                                 Pageable pageable) {
        return productRepository.findByName(productParameter.parameter(), pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
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
    public void updateNameId() {

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            product.setProductNameId(
                    transliterationService.getLatinStringLine(product.getName()));
        }
        productRepository.saveAll(products);

        List<Animal> animals = animalRepository.findAll();
        for (Animal animal : animals) {
            animal.setAnimalNameId(
                    transliterationService.getLatinStringLine(animal.getName()));
        }

        animalRepository.saveAll(animals);

        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            category.setCategoryNameId(
                    transliterationService.getLatinStringLine(category.getName()));
        }
        categoryRepository.saveAll(categories);

        List<PetService> petServices = petServiceRepository.findAll();

        for (PetService petService : petServices) {
            petService.setPetServiceNameId(
                    transliterationService.getLatinStringLine(petService.getName()));
        }

        petServiceRepository.saveAll(petServices);

        List<TypePetService> typePetServices = typePetServiceRepository.findAll();

        for (TypePetService petService : typePetServices) {
            petService.setTypePetServiceNameId(
                    transliterationService.getLatinStringLine(petService.getName()));
        }

        typePetServiceRepository.saveAll(typePetServices);
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
    public ProductResponseDto update(Long id,
                                     CreateRequestProductDto requestProductDto) {
        Product product = getProductFromDb(id);
        Product updateProduct = productMapper.toUpdate(requestProductDto, product);
        updateProduct.setProductNameId(
                transliterationService.getLatinStringLine(updateProduct.getName()));
        return productMapper.toDto(productRepository.save(updateProduct));
    }

    @Override
    public void delete(Long id) {
        Product product = getProductFromDb(id);
        List<String> urls = product.getImageUrls();
        for (String url : urls) {
            amazonS3Service.deleteImage(url);
        }

        productRepository.deleteById(id);
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
}
