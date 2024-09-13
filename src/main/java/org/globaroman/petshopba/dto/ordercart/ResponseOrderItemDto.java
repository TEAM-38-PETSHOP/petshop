package org.globaroman.petshopba.dto.ordercart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.globaroman.petshopba.dto.product.ProductResponseDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOrderItemDto implements Serializable {
    private Long id;
    private ProductResponseDto productDto;
    private int quantity;
    private BigDecimal price;
}
