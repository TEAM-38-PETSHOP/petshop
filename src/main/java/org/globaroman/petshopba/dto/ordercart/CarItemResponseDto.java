package org.globaroman.petshopba.dto.ordercart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.petshopba.dto.product.ProductResponseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarItemResponseDto {
    private Long cartItemId;
    private ProductResponseDto productDto;
    private int quantity;
}
