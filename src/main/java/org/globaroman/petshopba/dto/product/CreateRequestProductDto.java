package org.globaroman.petshopba.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRequestProductDto implements Serializable {
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
    private String composition;
    private String compositionAnalysis;
    private String compositionEnergyValue;
    private String compositionExpiration;
    private String instruction;
    private String instructionWhyBuy;
    private List<Long> animalsId;
    private List<Long> categoriesId;
}
