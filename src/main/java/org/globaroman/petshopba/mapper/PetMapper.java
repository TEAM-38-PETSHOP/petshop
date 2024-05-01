package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.pet.CreatePetAnimalRequestDto;
import org.globaroman.petshopba.dto.pet.ResponsePetAnimalDto;
import org.globaroman.petshopba.model.Pet;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = {
        UserMapper.class})
public interface PetMapper {

    @Mapping(target = "userId", source = "user", qualifiedByName = "idFromUser")
    ResponsePetAnimalDto toDto(Pet pet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    Pet partialUpdate(ResponsePetAnimalDto responsePetAnimalDto, @MappingTarget Pet pet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "petNameId", ignore = true)
    Pet toEntity(CreatePetAnimalRequestDto createPetAnimalRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "petNameId", ignore = true)
    Pet updatePet(CreatePetAnimalRequestDto requestDto, @MappingTarget Pet pet);
}
