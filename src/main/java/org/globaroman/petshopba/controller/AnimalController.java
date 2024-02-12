package org.globaroman.petshopba.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.animal.CreateAnimalRequestDto;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.service.AnimalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping
    public ResponseAnimalDto create(@RequestBody CreateAnimalRequestDto requestDto) {
        return animalService.create(requestDto);
    }

    @GetMapping
    public List<ResponseAnimalDto> getAllAnimal() {
        return animalService.getAll();
    }
}
