package org.globaroman.petshopba.dto.ordercart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarItemResponseDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private int quantity;
}
