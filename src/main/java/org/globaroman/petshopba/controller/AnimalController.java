package org.globaroman.petshopba.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.animal.CreateAnimalRequestDto;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAnimalDto create(@RequestBody CreateAnimalRequestDto requestDto) {
        return animalService.create(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseAnimalDto> getAllAnimal() {
        return animalService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseAnimalDto getAnimalByID(@PathVariable Long id) {
        return animalService.getAnimalById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseAnimalDto updateAnimalbyId(
            @PathVariable Long id,
            @RequestBody CreateAnimalRequestDto requestDto) {
        return animalService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnimalById(@PathVariable Long id) {
        animalService.delete(id);
    }
}
