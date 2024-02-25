package org.globaroman.petshopba.dto.grooming;

import lombok.Data;

@Data
public class ResponseTy {
    private Long id;
    private Long numberList;
    private String name;
    private String description;
    private Long animalId;
}
