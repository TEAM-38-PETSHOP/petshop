package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.user.CodeForNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.CreateNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserEmailForRecovePasswordRequestDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.RegistrationException;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto update(Long id, UpdateRoleDto updateRoleDto);

    List<UserResponseDto> getAll();

    void deleteById(Long id);

    UserResponseDto getUserInfo(Authentication authentication);

    boolean getCodeForNewPassword(UserEmailForRecovePasswordRequestDto requestDto);

    boolean compareCode(CodeForNewPasswordRequestDto requestDto);

    boolean resetPassword(CreateNewPasswordRequestDto requestDto);
}
