package org.globaroman.petshopba.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRequestCategoryDto implements Serializable {
    private String name;
    private String description;
}
