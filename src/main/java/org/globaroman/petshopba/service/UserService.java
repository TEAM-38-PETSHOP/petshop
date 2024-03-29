package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto update(Long id, UpdateRoleDto updateRoleDto);

    List<UserResponseDto> getAll();

    void deleteById(Long id);
}
