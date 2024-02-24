package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.grooming.CreatePoslugaRequestDto;
import org.globaroman.petshopba.dto.grooming.ResponsePoslugaDto;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.groom.Posluga;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = AnimalMapper.class)
public interface PoslugaMapper {

    @Mapping(target = "animal", ignore = true)
    Posluga toModel(CreatePoslugaRequestDto requestDto);

    @Mapping(target = "animalId", source = "animal.id")
    ResponsePoslugaDto toDto(Posluga posluga);

    @AfterMapping
    default void setAnimals(
            CreatePoslugaRequestDto requestDto,
            @MappingTarget Posluga posluga) {
        posluga.setAnimal(new Animal(requestDto.getAnimalId()));
    }
}
