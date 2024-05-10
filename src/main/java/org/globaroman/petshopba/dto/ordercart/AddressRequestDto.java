package org.globaroman.petshopba.dto.ordercart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequestDto {
    @NotBlank
    private String city;
    private String street;
    private String building;
    private String apartment;
    private String officeNovaPost;
}
