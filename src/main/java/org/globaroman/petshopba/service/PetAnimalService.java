package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.pet.CreatePetAnimalRequestDto;
import org.globaroman.petshopba.dto.pet.ResponsePetAnimalDto;
import org.springframework.security.core.Authentication;

public interface PetAnimalService {
    ResponsePetAnimalDto create(CreatePetAnimalRequestDto requestDto,
                                Authentication authentication);

    ResponsePetAnimalDto update(CreatePetAnimalRequestDto requestDto, Long petId);

    List<ResponsePetAnimalDto> getAll();

    ResponsePetAnimalDto getPetById(Long petId, Authentication authentication);

    List<ResponsePetAnimalDto> getAllPetByUserId(Long userId);

    void deleteById(Long petId, Authentication authentication);
}
