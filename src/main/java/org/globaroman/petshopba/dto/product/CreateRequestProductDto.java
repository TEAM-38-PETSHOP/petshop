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
    private String image;
    private List<Long> animalsId;
    private List<Long> categoriesId;
}
