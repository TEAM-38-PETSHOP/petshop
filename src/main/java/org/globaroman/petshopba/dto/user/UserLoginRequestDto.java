package org.globaroman.petshopba.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UserLoginRequestDto {
    @NotEmpty
    @Length(min = 8, max = 35)
    @Email
    private String email;
    @NotEmpty
    @Length(min = 8, max = 35)
    private String password;
}
