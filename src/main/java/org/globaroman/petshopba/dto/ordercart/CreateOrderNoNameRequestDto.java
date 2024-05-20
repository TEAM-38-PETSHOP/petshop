package org.globaroman.petshopba.dto.ordercart;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;
import org.globaroman.petshopba.validation.Phone;

@Data
public class CreateOrderNoNameRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Phone
    private String phone;
    @Email
    private String email;

    private AddressRequestDto address;

    private List<CartItemRequestDto> cartItems;
}
