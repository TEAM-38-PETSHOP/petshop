package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.grooming.ResponseTy;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;

public interface GroomService {
    List<ResponseTy> getAllSortedNumberList();

    void downloadService();

    void downloadTypeService();

    List<ResponseTypeServiceDto> getAllTypePetService();

    List<ResponseTy> getAllPetServiceByAnimalId(Long animalId);

    List<ResponseTypeServiceDto> getAllTypePetServiceByServiceId(Long serviceId);

    ResponseTy getPetServiceById(Long id);

    ResponseTypeServiceDto getTypePetServiceById(Long id);
}
