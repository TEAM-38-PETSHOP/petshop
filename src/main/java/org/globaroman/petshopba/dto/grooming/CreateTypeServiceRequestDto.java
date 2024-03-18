package org.globaroman.petshopba.dto.grooming;

import lombok.Data;

@Data
public class CreateTypeServiceRequestDto {
    private Long numberList;
    private String name;
    private String price;
    private Long petServiceId;
}
