package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.animal.CreateAnimalRequestDto;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;

public interface AnimalService {
    ResponseAnimalDto create(CreateAnimalRequestDto requestDto);

    List<ResponseAnimalDto> getAll();
}
