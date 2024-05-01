package org.globaroman.petshopba.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCategoryDto implements Serializable {
    private Long categoryId;
    private String name;
    private String categoryNameId;
    private String description;
}
