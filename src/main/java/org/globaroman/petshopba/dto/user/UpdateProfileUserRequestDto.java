package org.globaroman.petshopba.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.globaroman.petshopba.validation.FieldMatch;
import org.globaroman.petshopba.validation.Phone;
import org.globaroman.petshopba.validation.ValidPassword;

@Data
@FieldMatch(first = "newPassword", second = "newPasswordRepeat")
public class UpdateProfileUserRequestDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Phone
    private String phone;
    @ValidPassword
    private String oldPassword;
    @ValidPassword
    private String newPassword;
    @ValidPassword
    private String newPasswordRepeat;
}
