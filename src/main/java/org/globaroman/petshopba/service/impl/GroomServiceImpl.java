package org.globaroman.petshopba.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.grooming.CreatePoslugaRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponsePoslugaDto;
import org.globaroman.petshopba.exception.DataProcessingException;
import org.globaroman.petshopba.mapper.PoslugaMapper;
import org.globaroman.petshopba.model.groom.Posluga;
import org.globaroman.petshopba.repository.AnimalRepository;
import org.globaroman.petshopba.repository.PoslugaRepository;
import org.globaroman.petshopba.repository.TypePoslugaRepository;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.globaroman.petshopba.service.GroomService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroomServiceImpl implements GroomService {
    public static final String PATH_KEY_SERVICE = "services/listBasicService.txt";

    private final PoslugaRepository poslugaRepository;
    private final TypePoslugaRepository typePoslugaRepository;
    private final AmazonS3Service amazonS3Service;
    private final AnimalRepository animalRepository;
    private final PoslugaMapper poslugaMapper;

    @Override
    public List<ResponsePoslugaDto> getAllPosligu() {
        return poslugaRepository.findAll().stream()
                .map(poslugaMapper::toDto)
                .toList();
    }

    @Override
    public void downloadService() {

        try (InputStream inputStream = amazonS3Service.downloadS3(PATH_KEY_SERVICE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                CreatePoslugaRequestDto requestDto = getPoslugaRequestDto(line);
                Posluga model = poslugaMapper.toModel(requestDto);
                poslugaRepository.save(model);
            }

        } catch (IOException e) {
            throw new DataProcessingException("Can not download file: " + PATH_KEY_SERVICE, e);
        }
    }

    private CreatePoslugaRequestDto getPoslugaRequestDto(String line) {
        CreatePoslugaRequestDto requestDto = new CreatePoslugaRequestDto();
        String[] splitLine = line.split(",");
        requestDto.setDescription(splitLine[3]);
        requestDto.setName(splitLine[2]);
        requestDto.setAnimalId(Long.parseLong(splitLine[0]));
        requestDto.setNumberList(Long.parseLong(splitLine[1]));
        return requestDto;
    }
}
