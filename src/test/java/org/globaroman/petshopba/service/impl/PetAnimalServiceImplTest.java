package org.globaroman.petshopba.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.dto.pet.CreatePetAnimalRequestDto;
import org.globaroman.petshopba.dto.pet.ResponsePetAnimalDto;
import org.globaroman.petshopba.mapper.PetMapper;
import org.globaroman.petshopba.model.Pet;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.PetRepository;
import org.globaroman.petshopba.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class PetAnimalServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PetAnimalServiceImpl petAnimalService;

    private User user;

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        user = new User()
                .setId(1L)
                .setEmail("roman@gmail.com")
                .setPassword("$2a$10$2P9C9iZmpeNBNt2qrNKHcO7mxE/DcDV62TVvHa1OZpa1Ha3Hzi1Va")
                .setFirstName("John")
                .setLastName("Duo");

        authentication = new org.springframework.security
                .authentication.UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    @DisplayName("Create a new Pet -> return ResponseDto and and Result Ok")
    void create_ShouldCreateNewPet_ReturnResponseDto_ResultOk() {
        Pet pet = getPet();
        CreatePetAnimalRequestDto requestDto = getPetRequestDto();

        Mockito.when(petMapper.toEntity(requestDto)).thenReturn(pet);
        Mockito.when(petRepository.save(pet)).thenReturn(pet);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        ResponsePetAnimalDto response = getResponsePetDto();
        Mockito.when(petMapper.toDto(pet)).thenReturn(response);

        ResponsePetAnimalDto result = petAnimalService.create(requestDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestDto.getName(), response.getName());
    }

    @Test
    @DisplayName("Pet -> return ResponseDto and Result Ok")
    void update_ShouldUpdateExistPet_ReturnResponseDto_ResultOk() {
        Pet pet = getPet();
        Pet updatePet = getPet();
        updatePet.setName("newName");
        CreatePetAnimalRequestDto requestDto = getPetRequestDto();
        Mockito.when(petRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(pet));
        Mockito.when(petMapper.updatePet(requestDto, pet)).thenReturn(updatePet);
        Mockito.when(petRepository.save(updatePet)).thenReturn(updatePet);
        ResponsePetAnimalDto responsePetAnimalDto = getResponsePetDto();
        responsePetAnimalDto.setName("newName");

        Mockito.when(petMapper.toDto(updatePet)).thenReturn(responsePetAnimalDto);

        ResponsePetAnimalDto result = petAnimalService.update(requestDto, 1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatePet.getName(), responsePetAnimalDto.getName());
    }

    @Test
    @DisplayName("Pet -> return ResponseDto and Result Ok")
    void getAll_ShouldGetAllPets_ReturnListResponseDto_ResultOk() {
        Pet pet = getPet();
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);

        Mockito.when(petRepository.findAll()).thenReturn(pets);
        Mockito.when(petMapper.toDto(Mockito.any(Pet.class)))
                .thenReturn(new ResponsePetAnimalDto());

        List<ResponsePetAnimalDto> result = petAnimalService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Pet -> return ResponseDto and Result Ok")
    void getPetById_ShouldGetPetById_ReturnResponseDto_ResultOk() {
        Pet pet = getPet();
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);

        Mockito.when(petRepository.findAllByUserId(Mockito.anyLong())).thenReturn(pets);

        Assertions.assertThrows(RuntimeException.class, () -> {
            petAnimalService.getPetById(100L, authentication);
        });
    }

    @Test
    @DisplayName("Pet -> return ResponseDto and Result Ok")
    void getAllPetByUserId_ShouldGetAllPetsForExistUser_ReturnListResponseDto_ResultOk() {
        Pet pet = getPet();
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);

        Mockito.when(petRepository.findAllByUserId(Mockito.anyLong()))
                .thenReturn(pets);
        Mockito.when(petMapper.toDto(Mockito.any(Pet.class)))
                .thenReturn(new ResponsePetAnimalDto());

        List<ResponsePetAnimalDto> result = petAnimalService.getAllPetByUserId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    private Pet getPet() {
        Pet pet = new Pet();
        pet.setPetBreed("Breed");
        pet.setId(1L);
        pet.setSex("Girl");
        pet.setBirthDate(LocalDate.now());
        pet.setName("Name");
        return pet;
    }

    private CreatePetAnimalRequestDto getPetRequestDto() {
        CreatePetAnimalRequestDto requestDto = new CreatePetAnimalRequestDto();
        requestDto.setBirthDate(LocalDate.now());
        requestDto.setPetBreed("Breed");
        requestDto.setName("Name");
        requestDto.setSex("Girl");
        return requestDto;
    }

    private ResponsePetAnimalDto getResponsePetDto() {
        ResponsePetAnimalDto response = new ResponsePetAnimalDto();
        response.setPetBreed("Breed");
        response.setId(1L);
        response.setName("Name");
        response.setSex("Girl");
        response.setBirthDate(LocalDate.now());
        return response;
    }
}
