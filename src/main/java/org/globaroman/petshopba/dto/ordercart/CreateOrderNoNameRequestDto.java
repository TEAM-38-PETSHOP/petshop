package org.globaroman.petshopba.dto.ordercart;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.globaroman.petshopba.model.cartorder.Address;
import org.globaroman.petshopba.validation.Phone;

import java.util.List;

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
