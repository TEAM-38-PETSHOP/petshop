package org.globaroman.petshopba.dto.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePetAnimalDto implements Serializable {
    private Long id;
    private Long userId;
    private String name;
    private String petNameId;
    private String sex;
    private String petBreed;
    private LocalDate birthDate;
}
