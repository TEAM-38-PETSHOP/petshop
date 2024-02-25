package org.globaroman.petshopba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.grooming.ResponseTy;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;
import org.globaroman.petshopba.service.GroomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Grooming management",
        description = "endpoint for Grooming service management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grooms")
public class GroomController {
    private final GroomService groomService;

    @GetMapping("/admin/downloadService")
    @Operation(hidden = true)
    public void downloadServices() {
        groomService.downloadService();
    }

    @GetMapping("/admin/downloadTypeService")
    @Operation(hidden = true)
    public void downloadTypeServices() {
        groomService.downloadTypeService();
    }

    @GetMapping("/services")
    @Operation(summary = "Get all basic pet services",
            description = "You can get all basic pet services")
    public List<ResponseTy> getAllPetServices() {
        return groomService.getAllSortedNumberList();
    }

    @GetMapping("/typeOfServices")
    @Operation(summary = "Get all type of pet services",
            description = "You can get all type of pet services")
    public List<ResponseTypeServiceDto> getAllTypePetService() {
        return groomService.getAllTypePetService();
    }

    @GetMapping("/services/{animalId}")
    @Operation(summary = "Get all pet services by animal id",
            description = "You can get all pet services by animal id")
    public List<ResponseTy> getAllPetServiceByAnimalId(@PathVariable Long animalId) {
        return groomService.getAllPetServiceByAnimalId(animalId);
    }

    @GetMapping("/typeOfServices/{serviceId}")
    @Operation(summary = "Get all type of pet services by service id",
            description = "You can get all type of pet services by service id")
    public List<ResponseTypeServiceDto> getAllTypeServiceByServiceId(@PathVariable Long serviceId) {
        return groomService.getAllTypePetServiceByServiceId(serviceId);
    }

    @GetMapping("/service/{id}")
    @Operation(summary = "Get pet service by id",
            description = "You can get pet service by id")
    public ResponseTy getPetServiceById(@PathVariable Long id) {
        return groomService.getPetServiceById(id);
    }

    @GetMapping("/typeOfService/{id}")
    @Operation(summary = "Get type of pet services by id",
            description = "You can get type of pet services by id")
    public ResponseTypeServiceDto getTypePetServiceById(@PathVariable Long id) {
        return groomService.getTypePetServiceById(id);
    }
}
