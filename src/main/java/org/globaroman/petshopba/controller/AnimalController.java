package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Animal management",
        description = "endpoint for animal management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new animal",
            description = "You can create a new animal")
    public ResponseAnimalDto create(@RequestBody CreateAnimalRequestDto requestDto) {
        return animalService.create(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all animal",
            description = "You can get all animal from DataBase")
    public List<ResponseAnimalDto> getAllAnimal() {
        return animalService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get animal by its id",
            description = "You can get an animal by its id")
    public ResponseAnimalDto getAnimalByID(@PathVariable Long id) {
        return animalService.getAnimalById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update animal",
            description = "You can update an animal")
    public ResponseAnimalDto updateAnimalbyId(
            @PathVariable Long id,
            @RequestBody CreateAnimalRequestDto requestDto) {
        return animalService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete animal",
            description = "You can delete animal from DataBase")
    public void deleteAnimalById(@PathVariable Long id) {
        animalService.delete(id);
    }
}
