package org.globaroman.petshopba.dto.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishListResponseDto implements Serializable {
    private Long id;
    private Long userId;
    private Set<WishItemResponseDto> wishItems;
}
