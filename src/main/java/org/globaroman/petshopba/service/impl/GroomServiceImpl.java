package org.globaroman.petshopba.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.grooming.CreatePetServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.CreateTypeServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponsePetServiceDto;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;
import org.globaroman.petshopba.exception.DataProcessingException;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.mapper.PetServiceMapper;
import org.globaroman.petshopba.mapper.TypeServiceMapper;
import org.globaroman.petshopba.model.groom.PetService;
import org.globaroman.petshopba.model.groom.TypePetService;
import org.globaroman.petshopba.repository.PetServiceRepository;
import org.globaroman.petshopba.repository.TypePetServiceRepository;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.globaroman.petshopba.service.GroomService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroomServiceImpl implements GroomService {
    public static final String PATH_KEY_SERVICE = "services/listBasicService.txt";
    public static final String PATH_KEY_TYPE_SERVICE = "services/listPoslug.txt";

    private final PetServiceRepository petServiceRepository;
    private final TypePetServiceRepository typePetServiceRepository;
    private final AmazonS3Service amazonS3Service;
    private final PetServiceMapper petServiceMapper;
    private final TypeServiceMapper typeServiceMapper;

    @Override
    public ResponsePetServiceDto createPetService(
            CreatePetServiceRequestDto requestDto) {
        PetService petService = petServiceMapper.toModel(requestDto);
        return petServiceMapper.toDto(petServiceRepository.save(petService));
    }

    @Override
    public ResponseTypeServiceDto createTypeService(
            CreateTypeServiceRequestDto requestDto) {
        TypePetService typePetService = typeServiceMapper.toModel(requestDto);

        return typeServiceMapper.toDto(typePetServiceRepository.save(typePetService));
    }

    @Override
    public ResponsePetServiceDto updatePetService(
            CreatePetServiceRequestDto requestDto,
            Long id) {
        PetService petService = petServiceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find petService with id: " + id)
        );

        PetService updatePetService = petServiceMapper.toUpdateModel(
                requestDto,
                petService);
        return petServiceMapper.toDto(petServiceRepository.save(updatePetService));
    }

    @Override
    public ResponseTypeServiceDto updateTypePetService(
            CreateTypeServiceRequestDto requestDto,
            Long id) {
        TypePetService typePetService = typePetServiceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find typePetService with id: "
                        + id)
        );

        TypePetService updateTypeService = typeServiceMapper.toUpdateTypeService(
                requestDto,
                typePetService);

        return typeServiceMapper.toDto(typePetServiceRepository.save(updateTypeService));
    }

    @Override
    public void deleteServiceById(Long id) {
        petServiceRepository.deleteById(id);
    }

    @Override
    public void deleteTypeServiceById(Long id) {
        typePetServiceRepository.deleteById(id);
    }

    @Override
    public List<ResponsePetServiceDto> getAllSortedNumberList() {
        return petServiceRepository.findAllSortedNumberList().stream()
                .map(petServiceMapper::toDto)
                .toList();
    }

    @Override
    public void downloadService() {

        try (InputStream inputStream = amazonS3Service.downloadS3(PATH_KEY_SERVICE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                CreatePetServiceRequestDto requestDto = getPetServiceRequestDto(line);
                PetService petService = petServiceMapper.toModel(requestDto);
                petServiceRepository.save(petService);
            }

        } catch (IOException e) {
            throw new DataProcessingException("Can not download file: " + PATH_KEY_SERVICE, e);
        }
    }

    @Override
    public void downloadTypeService() {
        try (InputStream inputStream = amazonS3Service.downloadS3(PATH_KEY_TYPE_SERVICE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                CreateTypeServiceRequestDto requestDto = getTypePetServiceRequestDto(line);
                TypePetService typePetService = typeServiceMapper.toModel(requestDto);
                typePetServiceRepository.save(typePetService);
            }

        } catch (IOException e) {
            throw new DataProcessingException("Can not download file: " + PATH_KEY_SERVICE, e);
        }
    }

    @Override
    public List<ResponseTypeServiceDto> getAllTypePetService() {
        return typePetServiceRepository.findAll().stream()
                .map(typeServiceMapper::toDto)
                .toList();
    }

    @Override
    public List<ResponsePetServiceDto> getAllPetServiceByAnimalId(Long animalId) {
        return petServiceRepository.findAllByAnimalId(animalId).stream()
                .map(petServiceMapper::toDto)
                .toList();
    }

    @Override
    public List<ResponseTypeServiceDto> getAllTypePetServiceByServiceId(Long serviceId) {
        return typePetServiceRepository.findAllByPetServiceId(serviceId).stream()
                .map(typeServiceMapper::toDto)
                .toList();
    }

    @Override
    public ResponsePetServiceDto getPetServiceById(Long id) {
        PetService petService = petServiceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find petService with id:" + id)
        );

        return petServiceMapper.toDto(petService);
    }

    @Override
    public ResponseTypeServiceDto getTypePetServiceById(Long id) {
        TypePetService typePetService = typePetServiceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find typePetService with id:" + id)
        );
        return typeServiceMapper.toDto(typePetService);
    }

    private CreateTypeServiceRequestDto getTypePetServiceRequestDto(String line) {
        String[] splitLine = line.split(":");
        CreateTypeServiceRequestDto requestDto = new CreateTypeServiceRequestDto();
        requestDto.setName(splitLine[2]);
        requestDto.setPrice(splitLine[3]);
        requestDto.setPetServiceId(getPetServiceId(splitLine[0]));
        requestDto.setNumberList(Long.parseLong(splitLine[1]));
        return requestDto;
    }

    private Long getPetServiceId(String s) {
        Long petServiceId = Long.parseLong(s);
        PetService petService = petServiceRepository.findByNumberId(petServiceId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find PetService with numberId: "
                        + petServiceId)
        );

        return petService.getId();
    }

    private CreatePetServiceRequestDto getPetServiceRequestDto(String line) {
        CreatePetServiceRequestDto requestDto = new CreatePetServiceRequestDto();
        String[] splitLine = line.split(":");
        requestDto.setDescription(splitLine[3]);
        requestDto.setName(splitLine[2]);
        requestDto.setAnimalId(Long.parseLong(splitLine[0]));
        requestDto.setNumberList(Long.parseLong(splitLine[1]));
        return requestDto;
    }
}
