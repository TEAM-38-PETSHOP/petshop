package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.*;
import org.globaroman.petshopba.dto.pet.CreatePetAnimalRequestDto;
import org.globaroman.petshopba.dto.pet.ResponsePetAnimalDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.PetMapper;
import org.globaroman.petshopba.model.Pet;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.PetRepository;
import org.globaroman.petshopba.repository.UserRepository;
import org.globaroman.petshopba.service.PetAnimalService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PetAnimalServiceImpl implements PetAnimalService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final UserRepository userRepository;

    @Override
    public ResponsePetAnimalDto create(CreatePetAnimalRequestDto requestDto,
                                       Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Pet pet = petMapper.toEntity(requestDto);
        pet.setUser(user);
        Pet savedPet = petRepository.save(pet);
        userRepository.save(user);
        return petMapper.toDto(savedPet);
    }

    @Override
    public ResponsePetAnimalDto update(CreatePetAnimalRequestDto requestDto, Long petId) {
        Pet pet = getPetFromDataBase(petId);
        Pet updatePet = petMapper.updatePet(requestDto, pet);
        return petMapper.toDto(petRepository.save(updatePet));
    }

    @Override
    public List<ResponsePetAnimalDto> getAll() {
        return petRepository.findAll().stream()
                .map(petMapper::toDto)
                .toList();
    }

    @Override
    public ResponsePetAnimalDto getPetById(Long petId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if (petExists(user.getId(), petId)) {
            Pet pet = getPetFromDataBase(petId);
            return petMapper.toDto(pet);
        } else {
            log.error("You do not need permission for this operation");
            throw new RuntimeException("You do not need permission for this operation");
        }
    }

    @Override
    public List<ResponsePetAnimalDto> getAllPetByUserId(Long userId) {
        return petRepository.findAllByUserId(userId).stream()
                .map(petMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long petId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (petExists(user.getId(), petId)) {
            Pet pet = getPetFromDataBase(petId);
            petRepository.deleteById(petId);
        } else {
            log.error("You do not need permission for this operation");
            throw new RuntimeException("You do not need permission for this operation");
        }

    }

    private Pet getPetFromDataBase(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> {
                    log.error("Can not pet with ID: " + petId);
                    return new EntityNotFoundCustomException("Can not pet with ID: " + petId);
                }
        );
    }

    private boolean petExists(Long id, Long petId) {
        List<Pet> pets = petRepository.findAllByUserId(id);
        return pets.stream()
                .anyMatch(p -> {
                    return petId.equals(p.getId());
                });
    }
}
