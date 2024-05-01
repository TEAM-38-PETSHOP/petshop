package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.grooming.CreateTypeServiceRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponseTypeServiceDto;
import org.globaroman.petshopba.model.groom.PetService;
import org.globaroman.petshopba.model.groom.TypePetService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface TypeServiceMapper {

    @Mapping(target = "petService", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "typePetServiceNameId", ignore = true)
    TypePetService toModel(CreateTypeServiceRequestDto requestDto);

    @Mapping(target = "petServiceId", source = "petService.id")
    ResponseTypeServiceDto toDto(TypePetService typePetService);

    @AfterMapping
    default void setPerService(
            CreateTypeServiceRequestDto requestDto,
            @MappingTarget TypePetService typePetService) {
        typePetService.setPetService(new PetService(requestDto.getPetServiceId()));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "petService", ignore = true)
    @Mapping(target = "typePetServiceNameId", ignore = true)
    TypePetService toUpdateTypeService(CreateTypeServiceRequestDto requestDto,
                                       @MappingTarget TypePetService typePetService);
}
