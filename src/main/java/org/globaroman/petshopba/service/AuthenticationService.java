package org.globaroman.petshopba.service;

import org.globaroman.petshopba.dto.user.UserLoginRequestDto;
import org.globaroman.petshopba.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);
}
