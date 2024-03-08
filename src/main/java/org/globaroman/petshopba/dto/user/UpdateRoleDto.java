package org.globaroman.petshopba.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.petshopba.model.user.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {
    private Role.RoleName role;
}
