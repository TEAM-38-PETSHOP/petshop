package org.globaroman.petshopba.dto.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import org.globaroman.petshopba.dto.product.ProductResponseDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishItemResponseDto implements Serializable {
    private Long wishItemId;
    private ProductResponseDto product;
}
