package org.globaroman.petshopba.dto.animal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAnimalDto implements Serializable {
    private Long animalId;
    private String name;
    private String animalNameId;
}
