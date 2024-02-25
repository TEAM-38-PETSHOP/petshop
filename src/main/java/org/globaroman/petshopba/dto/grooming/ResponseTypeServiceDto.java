package org.globaroman.petshopba.dto.grooming;

import lombok.Data;

@Data
public class ResponseTypeServiceDto {
    private Long id;
    private Long poslugaId;
    private Long numberList;
    private String name;
    private String price;
}
