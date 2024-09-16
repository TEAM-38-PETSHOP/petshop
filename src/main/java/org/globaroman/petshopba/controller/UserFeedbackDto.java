package org.globaroman.petshopba.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserFeedbackDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
    private List<String> imageUrls;
}
