package org.globaroman.petshopba.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDto implements Serializable {
    private Long productId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private List<String> imageUrls;
    private String countryProduct;
    private String groupProduct;
    private String breedSize;
    private String type;
    private String packaging;
    private LocalDateTime entryDate;

    private List<ResponseAnimalDto> animals;
    private List<ResponseCategoryDto> categories;
}
