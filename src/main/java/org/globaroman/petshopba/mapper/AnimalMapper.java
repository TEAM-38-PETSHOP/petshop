package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.animal.CreateAnimalRequestDto;
import org.globaroman.petshopba.dto.animal.ResponseAnimalDto;
import org.globaroman.petshopba.model.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@SuppressWarnings("unmappedTargetProperties")
@Mapper(config = MapperConfig.class)
public interface AnimalMapper {

    ResponseAnimalDto toDto(Animal animal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products",ignore = true)
    Animal toModel(CreateAnimalRequestDto createAnimalRequestDto);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "products",ignore = true)
    Animal toUpdate(CreateAnimalRequestDto requestDto, @MappingTarget Animal animal);
}
