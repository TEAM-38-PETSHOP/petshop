package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.controller.UserFeedbackDto;
import org.globaroman.petshopba.dto.user.CodeForNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.CreateNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.UpdateProfileUserRequestDto;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserEmailForRecovePasswordRequestDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.RegistrationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto update(Long id, UpdateRoleDto updateRoleDto);

    List<UserResponseDto> getAll();

    String deleteById(Long id, Authentication authentication);

    UserResponseDto getUserInfo(Authentication authentication);

    boolean getCodeForNewPassword(UserEmailForRecovePasswordRequestDto requestDto);

    boolean compareCode(CodeForNewPasswordRequestDto requestDto);

    boolean resetPassword(CreateNewPasswordRequestDto requestDto);

    UserResponseDto updateProfile(UpdateProfileUserRequestDto requestDto,
                                  Authentication authentication);

    void handleFeedback(String message,
                        String experience,
                        MultipartFile[] file,
                        Authentication authentication);

    List<UserFeedbackDto> getAllFeedback();
}
