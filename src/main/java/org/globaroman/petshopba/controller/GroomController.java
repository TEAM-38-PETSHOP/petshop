package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.grooming.CreatePetServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.CreateTypeServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponsePetServiceDto;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;
import org.globaroman.petshopba.service.GroomService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://*")
@Tag(name = "Grooming management",
        description = "endpoint for Grooming service management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grooms")
public class GroomController {
    private final GroomService groomService;

    @PostMapping("/services")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create pet services",
            description = "You can create new pet services")
    public ResponsePetServiceDto createPetService(
            @RequestBody CreatePetServiceRequestDto requestDto) {
        return groomService.createPetService(requestDto);
    }

    @PostMapping("/typeOfServices")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new type pet services",
            description = "You can create a new type pet services")
    public ResponseTypeServiceDto createTypeOfService(
            @RequestBody CreateTypeServiceRequestDto requestDto) {
        return groomService.createTypeService(requestDto);
    }

    @PatchMapping("/services/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the pet services",
            description = "You can update pet services")
    public ResponsePetServiceDto updatePetService(
            @RequestBody CreatePetServiceRequestDto requestDto,
            @PathVariable Long id) {
        return groomService.updatePetService(requestDto, id);
    }

    @PatchMapping("/typeOfServices/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the type pet services",
            description = "You can update the type pet services")
    public ResponseTypeServiceDto updateTypePetService(
            @RequestBody CreateTypeServiceRequestDto requestDto,
            @PathVariable Long id) {
        return groomService.updateTypePetService(requestDto, id);
    }

    @DeleteMapping("/services/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete the pet services by id",
            description = "You can delete the pet services by id")
    public void deletePetServiceById(@PathVariable Long id) {
        groomService.deleteServiceById(id);
    }

    @DeleteMapping("/typeOfServices/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete the type pet services by id",
            description = "You can delete type pet services by id")
    public void deleteTypePetServiceById(@PathVariable Long id) {
        groomService.deleteTypeServiceById(id);
    }

    @GetMapping("/admin/downloadService")
    @ResponseStatus(HttpStatus.OK)
    @Operation(hidden = true)
    public void downloadServices() {
        groomService.downloadService();
    }

    @GetMapping("/admin/downloadTypeService")
    @ResponseStatus(HttpStatus.OK)
    @Operation(hidden = true)
    public void downloadTypeServices() {
        groomService.downloadTypeService();
    }

    @GetMapping("/services")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all basic pet services",
            description = "You can get all basic pet services")
    public List<ResponsePetServiceDto> getAllPetServices() {
        return groomService.getAllSortedNumberList();
    }

    @GetMapping("/typeOfServices")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all type of pet services",
            description = "You can get all type of pet services")
    public List<ResponseTypeServiceDto> getAllTypePetService() {
        return groomService.getAllTypePetService();
    }

    @GetMapping("/services/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all pet services by animal id",
            description = "You can get all pet services by animal id")
    public List<ResponsePetServiceDto> getAllPetServiceByAnimalId(@PathVariable Long animalId) {
        return groomService.getAllPetServiceByAnimalId(animalId);
    }

    @GetMapping("/typeOfServices/{serviceId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all type of pet services by service id",
            description = "You can get all type of pet services by service id")
    public List<ResponseTypeServiceDto> getAllTypeServiceByServiceId(@PathVariable Long serviceId) {
        return groomService.getAllTypePetServiceByServiceId(serviceId);
    }

    @GetMapping("/service/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get pet service by id",
            description = "You can get pet service by id")
    public ResponsePetServiceDto getPetServiceById(@PathVariable Long id) {
        return groomService.getPetServiceById(id);
    }

    @GetMapping("/typeOfService/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get type of pet services by id",
            description = "You can get type of pet services by id")
    public ResponseTypeServiceDto getTypePetServiceById(@PathVariable Long id) {
        return groomService.getTypePetServiceById(id);
    }
}
