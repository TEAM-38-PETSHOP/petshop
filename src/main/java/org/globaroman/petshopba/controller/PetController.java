package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.pet.CreatePetAnimalRequestDto;
import org.globaroman.petshopba.dto.pet.ResponsePetAnimalDto;
import org.globaroman.petshopba.security.PetAnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Pets", description = "Pet management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {
    private final PetAnimalService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Pet",
            description = "To create a new Pet format of Date - yyyy-mm-dd")
    public ResponsePetAnimalDto create(@RequestBody CreatePetAnimalRequestDto requestDto,
                                       Authentication authentication) {
        return petService.create(requestDto, authentication);
    }

    @PatchMapping("/{petId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exist Pet")
    public ResponsePetAnimalDto update(@RequestBody CreatePetAnimalRequestDto requestDto,
                                       @PathVariable Long petId) {
        return petService.update(requestDto, petId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Pets", description = "To get all pets You need Role - ADMIN")
    public List<ResponsePetAnimalDto> getAll() {
        return petService.getAll();
    }

    @GetMapping("/{petId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Pet by its ID")
    public ResponsePetAnimalDto getPetAnimalById(@PathVariable Long petId,
                                                 Authentication authentication) {
        return petService.getPetById(petId, authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Pets by userID",
            description = "To get all pets You need Role - ADMIN")
    public List<ResponsePetAnimalDto> getPetByUserId(@PathVariable Long userId) {
        return petService.getAllPetByUserId(userId);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Pet by its ID")
    public void deleteById(@PathVariable Long petId,
                           Authentication authentication) {
        petService.deleteById(petId, authentication);
    }
}
