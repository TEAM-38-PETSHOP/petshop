package org.globaroman.petshopba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.dto.grooming.CreatePetServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.CreateTypeServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponsePetServiceDto;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;
import org.globaroman.petshopba.mapper.PetServiceMapper;
import org.globaroman.petshopba.mapper.TypeServiceMapper;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.groom.PetService;
import org.globaroman.petshopba.model.groom.TypePetService;
import org.globaroman.petshopba.repository.PetServiceRepository;
import org.globaroman.petshopba.repository.TypePetServiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GroomServiceImplTest {
    @Mock
    private PetServiceRepository petServiceRepository;

    @Mock
    private TypePetServiceRepository typePetServiceRepository;

    @Mock
    private TypeServiceMapper typeServiceMapper;
    @Mock
    private PetServiceMapper petServiceMapper;
    @InjectMocks
    private GroomServiceImpl groomService;

    @Test
    @DisplayName("Create a new PetService -> return Status Created")
    void createPetService_ShouldSaveNewPetService_ReturnResponseDto() {
        PetService petService = getPetService();
        CreatePetServiceRequestDto requestDto = getRequestPetServiceDto();

        Mockito.when(petServiceMapper.toModel(requestDto)).thenReturn(petService);
        Mockito.when(petServiceRepository.save(petService)).thenReturn(petService);
        Mockito.when(petServiceMapper.toDto(petService)).thenReturn(new ResponsePetServiceDto());

        ResponsePetServiceDto result = groomService.createPetService(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestDto.getName(), petService.getName());
    }

    @Test
    @DisplayName("Create a new TypePetService -> return Status Created")
    void createTypeService_ShouldSaveNewTypePetService_ReturnResponseDto() {
        TypePetService typePetService = getTypePetService();
        CreateTypeServiceRequestDto requestDto = getRequestTypeServiceDto();

        Mockito.when(typeServiceMapper.toModel(requestDto)).thenReturn(typePetService);
        Mockito.when(typePetServiceRepository.save(typePetService)).thenReturn(typePetService);
        Mockito.when(typeServiceMapper.toDto(typePetService))
                .thenReturn(new ResponseTypeServiceDto());

        ResponseTypeServiceDto result = groomService.createTypeService(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestDto.getName(), typePetService.getName());
    }

    @Test
    @DisplayName("Update a exist PetService by Id")
    void updatePetService_ShouldUpdateExistPetService_returnResponseDto() {
        CreatePetServiceRequestDto requestDto = getRequestPetServiceDto();
        PetService petService = getPetService();

        Mockito.when(petServiceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(petService));
        Mockito.when(petServiceMapper.toUpdateModel(requestDto, petService))
                .thenReturn(petService);
        Mockito.when(petServiceRepository.save(petService)).thenReturn(petService);
        Mockito.when(petServiceMapper.toDto(petService)).thenReturn(new ResponsePetServiceDto());

        ResponsePetServiceDto result = groomService.updatePetService(requestDto, 1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestDto.getName(), petService.getName());
    }

    @Test
    @DisplayName("Update a exist TypePetService by Id")
    void updateTypePetService_ShouldUpdateExistTypePetService_returnResponseDto() {
        CreateTypeServiceRequestDto requestDto = getRequestTypeServiceDto();
        TypePetService typePetService = getTypePetService();

        Mockito.when(typePetServiceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(typePetService));
        Mockito.when(typeServiceMapper.toUpdateTypeService(requestDto, typePetService))
                .thenReturn(typePetService);
        Mockito.when(typePetServiceRepository.save(typePetService)).thenReturn(typePetService);
        Mockito.when(typeServiceMapper.toDto(typePetService))
                .thenReturn(new ResponseTypeServiceDto());

        ResponseTypeServiceDto result = groomService.updateTypePetService(requestDto, 1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestDto.getName(), typePetService.getName());
    }

    @Test
    @DisplayName("Delete a exist PetService by Id")
    void deleteServiceById_ShouldDeleteExistPetService_ResultOk() {
        groomService.deleteServiceById(1L);
        Mockito.verify(petServiceRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a exist TypePetService by Id")
    void deleteTypeServiceById_ShouldDeleteExistTypePetService_ResultOk() {
        TypePetService typePetService = getTypePetService();
        groomService.deleteTypeServiceById(typePetService.getId());
        Mockito.verify(typePetServiceRepository, Mockito.times(1))
                .deleteById(typePetService.getId());
    }

    @Test
    @DisplayName("Get list with sorted by field of numberList -> result ok")
    void getAllSortedNumberList_ShouldReturnListWithSortedDto() {
        List<PetService> listPets = new ArrayList<>();
        PetService petService = getPetService();
        PetService petService1 = getPetService();
        petService1.setNumberList(100102L);
        listPets.add(petService);
        listPets.add(petService1);

        Mockito.when(petServiceRepository.findAllSortedNumberList()).thenReturn(listPets);
        Mockito.when(petServiceMapper.toDto(Mockito.any(PetService.class)))
                .thenReturn(new ResponsePetServiceDto());

        List<ResponsePetServiceDto> result = groomService.getAllSortedNumberList();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Get all Type of PetService like List of ResponseTypePetServiceDto -> result Ok")
    void getAllTypePetService_ShouldReturnListWithResponseDto() {
        List<TypePetService> typePetServices = new ArrayList<>();
        TypePetService typePetService = getTypePetService();
        typePetServices.add(typePetService);

        Mockito.when(typePetServiceRepository.findAll()).thenReturn(typePetServices);
        Mockito.when(typeServiceMapper.toDto(Mockito.any(TypePetService.class)))
                .thenReturn(new ResponseTypeServiceDto());

        List<ResponseTypeServiceDto> result = groomService.getAllTypePetService();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get all PetService by Animal ID -> result ok")
    void getAllPetServiceByAnimalId_ShouldReturnListWithResponsePetServiceDto() {
        List<PetService> petServices = new ArrayList<>();
        PetService petService = getPetService();
        petServices.add(petService);

        Mockito.when(petServiceRepository.findAllByAnimalId(Mockito.anyLong()))
                .thenReturn(petServices);
        Mockito.when(petServiceMapper.toDto(Mockito.any(PetService.class)))
                .thenReturn(new ResponsePetServiceDto());

        List<ResponsePetServiceDto> result = groomService.getAllPetServiceByAnimalId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get all Type of PetService by PetService ID -> result Ok")
    void getAllTypePetServiceByServiceId_ShouldReturnListWithResponseTypePetServiceDto() {
        List<TypePetService> typePetServices = new ArrayList<>();
        TypePetService typePetService = getTypePetService();
        typePetServices.add(typePetService);

        Mockito.when(typePetServiceRepository.findAllByPetServiceId(Mockito.anyLong()))
                .thenReturn(typePetServices);
        Mockito.when(typeServiceMapper.toDto(Mockito.any(TypePetService.class)))
                .thenReturn(new ResponseTypeServiceDto());

        List<ResponseTypeServiceDto> result = groomService.getAllTypePetServiceByServiceId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get all PetService by ID -> result ok")
    void getPetServiceById_ShouldReturnResponsePetServiceDto() {
        PetService petService = getPetService();
        ResponsePetServiceDto responsePetServiceDto = getResponseDto();

        Mockito.when(petServiceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(petService));
        Mockito.when(petServiceMapper.toDto(petService)).thenReturn(responsePetServiceDto);

        ResponsePetServiceDto result = groomService.getPetServiceById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(petService.getName(), responsePetServiceDto.getName());
    }

    @Test
    @DisplayName("Get Type of PetService by ID -> result Ok")
    void getTypePetServiceById_ShouldReturnResponseTypePetServiceDto() {
        TypePetService typePetService = getTypePetService();
        ResponseTypeServiceDto responseTypeServiceDto = getResponseTypeServiceDto();

        Mockito.when(typePetServiceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(typePetService));
        Mockito.when(typeServiceMapper.toDto(typePetService))
                .thenReturn(responseTypeServiceDto);

        ResponseTypeServiceDto result = groomService.getTypePetServiceById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(typePetService.getName(), responseTypeServiceDto.getName());
    }

    private PetService getPetService() {
        PetService petService = new PetService();
        petService.setId(1L);
        petService.setDescription("DESCRIPTION PETSERVICE");
        petService.setName("NAME");
        petService.setAnimal(new Animal());
        petService.setNumberList(100101L);
        return petService;
    }

    private CreatePetServiceRequestDto getRequestPetServiceDto() {
        CreatePetServiceRequestDto requestDto = new CreatePetServiceRequestDto();
        requestDto.setDescription("DESCRIPTION PETSERVICE");
        requestDto.setAnimalId(1L);
        requestDto.setName("NAME");
        requestDto.setNumberList(100101L);
        return requestDto;
    }

    private ResponsePetServiceDto getResponseDto() {
        ResponsePetServiceDto responseDto = new ResponsePetServiceDto();
        responseDto.setId(1L);
        responseDto.setDescription("DESCRIPTION PETSERVICE");
        responseDto.setName("NAME");
        responseDto.setAnimalId(1L);
        responseDto.setNumberList(100101L);

        return responseDto;
    }

    private TypePetService getTypePetService() {
        TypePetService typePetService = new TypePetService();
        typePetService.setId(1L);
        typePetService.setPetService(getPetService());
        typePetService.setPrice("100");
        typePetService.setName("NAME TS");
        typePetService.setNumberList(100101L);
        return typePetService;
    }

    private CreateTypeServiceRequestDto getRequestTypeServiceDto() {
        CreateTypeServiceRequestDto requestDto = new CreateTypeServiceRequestDto();
        requestDto.setNumberList(100101L);
        requestDto.setName("NAME TS");
        requestDto.setPrice("100");
        requestDto.setPetServiceId(1L);
        return requestDto;
    }

    private ResponseTypeServiceDto getResponseTypeServiceDto() {
        ResponseTypeServiceDto responseDto = new ResponseTypeServiceDto();
        responseDto.setId(1L);
        responseDto.setPrice("100");
        responseDto.setName("NAME TS");
        responseDto.setPetServiceId(1L);
        responseDto.setNumberList(100101L);
        return responseDto;
    }
}
