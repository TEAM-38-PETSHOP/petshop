package org.globaroman.petshopba.service;

import java.util.List;
import org.globaroman.petshopba.dto.grooming.ResponsePoslugaDto;

public interface GroomService {
    List<ResponsePoslugaDto> getAllPosligu();

    void downloadService();
}
