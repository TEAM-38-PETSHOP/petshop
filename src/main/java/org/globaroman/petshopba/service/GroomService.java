package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.grooming.CreatePetServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.CreateTypeServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponsePetServiceDto;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;

public interface GroomService {
    List<ResponsePetServiceDto> getAllSortedNumberList();

    void downloadService();

    void downloadTypeService();

    List<ResponseTypeServiceDto> getAllTypePetService();

    List<ResponsePetServiceDto> getAllPetServiceByAnimalId(Long animalId);

    List<ResponseTypeServiceDto> getAllTypePetServiceByServiceId(Long serviceId);

    ResponsePetServiceDto getPetServiceById(Long id);

    ResponseTypeServiceDto getTypePetServiceById(Long id);

    ResponsePetServiceDto createPetService(CreatePetServiceRequestDto requestDto);

    ResponseTypeServiceDto createTypeService(CreateTypeServiceRequestDto requestDto);

    ResponsePetServiceDto updatePetService(CreatePetServiceRequestDto requestDto, Long id);

    ResponseTypeServiceDto updateTypePetService(CreateTypeServiceRequestDto requestDto, Long id);

    void deleteServiceById(Long id);

    void deleteTypeServiceById(Long id);
}
