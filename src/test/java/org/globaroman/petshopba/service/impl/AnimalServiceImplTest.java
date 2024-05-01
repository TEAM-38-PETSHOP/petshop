package org.globaroman.petshopba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.dto.animal.CreateAnimalRequestDto;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.mapper.AnimalMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.repository.AnimalRepository;
import org.globaroman.petshopba.service.TransliterationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    @Mock
    private TransliterationService transliterationService;

    @InjectMocks
    private AnimalServiceImpl animalService;

    @Test
    @DisplayName("Create a animal -> get a ResponseDto like successful result")
    void create_Animal_ShouldCreateNewAnimalAndReturnResponseDto() {
        Animal animal = getAnimal();
        CreateAnimalRequestDto requestDto = getRequestDto();
        ResponseAnimalDto responseAnimalDto = getResponseDto();

        Mockito.when(animalMapper.toModel(requestDto)).thenReturn(animal);
        Mockito.when(animalRepository.save(animal)).thenReturn(animal);
        Mockito.when(animalMapper.toDto(animal)).thenReturn(responseAnimalDto);
        Mockito.when(transliterationService.getLatinStringLine("Animal"))
                .thenReturn(Mockito.anyString());

        ResponseAnimalDto result = animalService.create(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getAnimalId());
    }

    @Test
    @DisplayName("Get all animals -> get List<ResponseDto> as successful result")
    void getAll_Animal_ShouldReturnListResponseDtos() {
        Animal animal = getAnimal();
        List<Animal> animals = new ArrayList<>();
        animals.add(animal);

        Mockito.when(animalRepository.findAll()).thenReturn(animals);
        Mockito.when(animalMapper.toDto(Mockito.any(Animal.class)))
                .thenReturn(new ResponseAnimalDto());

        List<ResponseAnimalDto> result = animalService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get animal by its id -> get successful result")
    void getAnimalById_Animal_ShouldReturnExistAnimalAsResponseDto() {
        Animal animal = getAnimal();
        ResponseAnimalDto responseAnimalDto = getResponseDto();

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        Mockito.when(animalMapper.toDto(animal)).thenReturn(responseAnimalDto);

        ResponseAnimalDto result = animalService.getAnimalById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getAnimalId());
    }

    @Test
    @DisplayName("Update the exist animal -> get successful result")
    void update_Animal_ShouldUpdateExistAnimalAndReturnResponseDto() {
        CreateAnimalRequestDto requestDto = getRequestDto();
        requestDto.setName("New name");
        Animal animal = getAnimal();
        ResponseAnimalDto responseAnimalDto = getResponseDto();
        responseAnimalDto.setName("New name");

        Animal updateAnimal = getAnimal();
        updateAnimal.setName("New name");

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        Mockito.when(animalMapper.toUpdate(requestDto, animal)).thenReturn(updateAnimal);
        Mockito.when(animalRepository.save(updateAnimal)).thenReturn(updateAnimal);
        Mockito.when(animalMapper.toDto(updateAnimal)).thenReturn(responseAnimalDto);
        Mockito.when(transliterationService.getLatinStringLine("New name"))
                .thenReturn(Mockito.anyString());

        ResponseAnimalDto result = animalService.update(1L, requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getAnimalId());
        Assertions.assertEquals(requestDto.getName(), result.getName());

    }

    @Test
    @DisplayName("Delete the exist animal -> get successful result")
    void delete_Animal_ShouldDeleteExistAnimalAndReturnResponseDto() {
        Animal animal = getAnimal();

        animalService.delete(animal.getId());

        Mockito.verify(animalRepository, Mockito.times(1)).deleteById(1L);

    }

    private CreateAnimalRequestDto getRequestDto() {
        CreateAnimalRequestDto requestDto = new CreateAnimalRequestDto();

        requestDto.setDescription("This is a description of the animal");
        requestDto.setName("Animal");
        return requestDto;
    }

    private ResponseAnimalDto getResponseDto() {
        ResponseAnimalDto responseAnimalDto = new ResponseAnimalDto();
        responseAnimalDto.setAnimalId(1L);
        responseAnimalDto.setName("Animal");
        return responseAnimalDto;
    }

    private Animal getAnimal() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setDescription("This is a description of the animal");
        animal.setName("Animal");
        return animal;
    }
}
