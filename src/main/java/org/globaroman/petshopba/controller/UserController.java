package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete exist an exist user")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/info")
    public UserResponseDto getUserInfo(Authentication authentication) {
        return userService.getUserInfo(authentication);
    }
}
