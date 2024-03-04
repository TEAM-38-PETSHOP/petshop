package org.globaroman.petshopba.dto.cart;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CarItemResponseDto> cartItems;
}
