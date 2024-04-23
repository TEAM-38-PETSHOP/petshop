package org.globaroman.petshopba.dto.product;

import java.util.List;
import lombok.Data;

@Data
public class RequestUpdateImageToProductDto {
    private List<String> imageUrls;
}
