package org.globaroman.petshopba.dto.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishItemRequestDto implements Serializable {
    private Long productId;
}
