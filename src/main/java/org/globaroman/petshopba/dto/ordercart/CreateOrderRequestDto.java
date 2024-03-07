package org.globaroman.petshopba.dto.ordercart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequestDto implements Serializable {
    @NotNull
    private String city;
    private String street;
    private String building;
    private String apartment;
    private String officeNovaPost;
}
