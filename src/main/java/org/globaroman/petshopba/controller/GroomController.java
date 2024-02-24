package org.globaroman.petshopba.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.grooming.ResponsePoslugaDto;
import org.globaroman.petshopba.service.GroomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grooms")
public class GroomController {
    private final GroomService groomService;

    @GetMapping("/admin/downloadService")
    public void downloadServices() {
        groomService.downloadService();
    }

    @GetMapping("/services")
    public List<ResponsePoslugaDto> getAllPoslugu() {
        return groomService.getAllPosligu();
    }

}
