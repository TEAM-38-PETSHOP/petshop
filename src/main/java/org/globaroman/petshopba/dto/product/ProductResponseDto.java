package org.globaroman.petshopba.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.dto.category.ResponseCategoryDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDto implements Serializable {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private String image;
    private List<ResponseAnimalDto> animals;
    private List<ResponseCategoryDto> categories;
}
