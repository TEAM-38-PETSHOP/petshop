package org.globaroman.petshopba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.animal.CreateAnimalRequestDto;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.AnimalMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.repository.AnimalRepository;
import org.globaroman.petshopba.service.AnimalService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public ResponseAnimalDto create(CreateAnimalRequestDto requestDto) {
        Animal animal = animalMapper.toModel(requestDto);
        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    public List<ResponseAnimalDto> getAll() {
        return animalRepository.findAll().stream()
                .map(animalMapper::toDto)
                .toList();
    }

    @Override
    public ResponseAnimalDto getAnimalById(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find animal with id: " + id)
        );

        return animalMapper.toDto(animal);
    }
}
