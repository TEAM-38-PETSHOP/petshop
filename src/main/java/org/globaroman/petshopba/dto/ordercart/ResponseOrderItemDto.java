package org.globaroman.petshopba.dto.ordercart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.globaroman.petshopba.model.Product;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOrderItemDto implements Serializable {
    private Long id;
    private Product product;
    private int quantity;
    private BigDecimal price;
}
