package org.globaroman.petshopba.dto.grooming;

import lombok.Data;

@Data
public class ResponseTypeServiceDto {
    private Long id;
    private Long petServiceId;
    private Long numberList;
    private String name;
    private String typePetServiceNameId;
    private String price;
}
