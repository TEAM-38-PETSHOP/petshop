package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.grooming.ResponsePetServiceDto;
import org.globaroman.petshopba.dto.grooming.CreatePetServiceRequestDto;
import org.globaroman.petshopba.model.Animal;
import org.globaroman.petshopba.model.groom.PetService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = AnimalMapper.class)
public interface PetServiceMapper {

    @Mapping(target = "animal", ignore = true)
    PetService toModel(CreatePetServiceRequestDto requestDto);

    @Mapping(target = "animalId", source = "animal.id")
    ResponsePetServiceDto toDto(PetService posluga);

    @AfterMapping
    default void setAnimals(
            CreatePetServiceRequestDto requestDto,
            @MappingTarget PetService posluga) {
        posluga.setAnimal(new Animal(requestDto.getAnimalId()));
    }

    @Mapping(target = "id", ignore = true)
    PetService toUpdateModel(CreatePetServiceRequestDto requestDto, @MappingTarget PetService petService);
}
