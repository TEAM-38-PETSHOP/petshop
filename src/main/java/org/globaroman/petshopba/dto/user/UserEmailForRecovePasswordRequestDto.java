package org.globaroman.petshopba.dto.user;

import jakarta.validation.constraints.Email;

public record UserEmailForRecovePasswordRequestDto(
        @Email(message = "Invalid email format") String email) {
}
