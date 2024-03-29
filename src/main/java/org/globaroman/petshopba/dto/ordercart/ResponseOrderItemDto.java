package org.globaroman.petshopba.dto.ordercart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOrderItemDto implements Serializable {
    private Long id;
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
