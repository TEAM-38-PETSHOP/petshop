package org.globaroman.petshopba.dto.ordercart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequestDto implements Serializable {
    @NotBlank
    private String city;
    private String street;
    private String building;
    private String apartment;
    private String officeNovaPost;
    private String comment;
}
