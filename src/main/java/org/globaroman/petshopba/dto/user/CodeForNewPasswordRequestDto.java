package org.globaroman.petshopba.dto.user;

import jakarta.validation.constraints.NotBlank;

public record CodeForNewPasswordRequestDto(@NotBlank String code) {
}
