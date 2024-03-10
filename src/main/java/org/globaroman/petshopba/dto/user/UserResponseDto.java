package org.globaroman.petshopba.dto.user;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private Set<RoleDto> roles;
}
