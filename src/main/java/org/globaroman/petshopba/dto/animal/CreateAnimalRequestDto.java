package org.globaroman.petshopba.dto.animal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAnimalRequestDto implements Serializable {
    private String name;
    private String description;
}
