package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.user.UpdateProfileUserRequestDto;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Users", description = "Operations related to users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exist user")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody UpdateRoleDto updateRoleDto) {
        return userService.update(id, updateRoleDto);
    }

    @PostMapping("/profile-update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exist user's profile")
    public UserResponseDto updateProfile(@Valid @RequestBody UpdateProfileUserRequestDto requestDto,
                                         Authentication authentication) {
        return userService.updateProfile(requestDto, authentication);
    }

    @PostMapping("/feedbacks")
    @Operation(summary = "Feedback from an exist user's profile")
    public ResponseEntity<String> handleFeedBackFromUser(@RequestParam("message") String message,
                                                         @RequestParam("experience")
                                                         String experience,
                                                      @RequestParam("files") MultipartFile[]files,
                                                         Authentication authentication) {

        userService.handleFeedback(message, experience, files, authentication);
        return ResponseEntity.ok("Feedback received successfully!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("feedbacks")
    @Operation(summary = "Get all feedback. Allowing only for ADMIN")
    public List<UserFeedbackDto> getAllFeedback() {
        return userService.getAllFeedback();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete exist user")
    public String delete(@PathVariable Long id,
                       Authentication authentication) {
        return userService.deleteById(id, authentication);
    }

    @GetMapping("/info")
    public UserResponseDto getUserInfo(Authentication authentication) {
        return userService.getUserInfo(authentication);
    }
}
