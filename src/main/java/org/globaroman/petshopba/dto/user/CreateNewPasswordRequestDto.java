package org.globaroman.petshopba.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateNewPasswordRequestDto(
        @NotBlank String code, @NotBlank @Min(8) String password) {
}
