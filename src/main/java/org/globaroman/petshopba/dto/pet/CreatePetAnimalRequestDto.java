package org.globaroman.petshopba.dto.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePetAnimalRequestDto implements Serializable {
    private String name;
    private String sex;
    private String petBreed;
    private LocalDate birthDate;
}
