package org.globaroman.petshopba.dto.ordercart;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemRequestDto {
    private List<CartItemRequestDto> cartItemRequestDtos;
}
