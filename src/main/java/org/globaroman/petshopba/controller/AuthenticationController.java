package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.user.CodeForNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.CreateNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.UserEmailForRecovePasswordRequestDto;
import org.globaroman.petshopba.dto.user.UserLoginRequestDto;
import org.globaroman.petshopba.dto.user.UserLoginResponseDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.RegistrationException;
import org.globaroman.petshopba.service.AuthenticationService;
import org.globaroman.petshopba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "Operations related to users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Log into account")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Action user, when forgot password send email")
    public ResponseEntity<String> forgotPassword(
            @RequestBody @Valid UserEmailForRecovePasswordRequestDto requestDto) {
        boolean isCodeSent = userService.getCodeForNewPassword(requestDto);
        if (isCodeSent) {
            return ResponseEntity
                    .ok("Код для відновлення паролю відправлено на вашу електронну пошту");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Щось пішло не так. Перевірте вашу електронну пошту та введіть її знову");
        }
    }

    @PostMapping("/send-code")
    @Operation(summary = "Action user, when forgot password send the code, received by email")
    public ResponseEntity<String> forgotPassword(
            @RequestBody @Valid CodeForNewPasswordRequestDto requestDto) {
        boolean isCodeEquals = userService.compareCode(requestDto);
        if (isCodeEquals) {
            return ResponseEntity.ok("Код прийнято. Ви можете встановити новий пароль.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Невірний код. Спробуйте ще раз.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody @Valid CreateNewPasswordRequestDto requestDto) {
        boolean isPasswordReset = userService.resetPassword(requestDto);
        if (isPasswordReset) {
            return ResponseEntity
                    .ok("Пароль змінено. Ви можете увіййти до свого облікового запису.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Щось пішло не так. Спробуйте ще раз.");
        }
    }

}
