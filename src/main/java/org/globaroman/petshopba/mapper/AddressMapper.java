package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.ordercart.AddressRequestDto;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.ResponseAddressDto;
import org.globaroman.petshopba.model.cartorder.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address toEntity(CreateOrderRequestDto createOrderRequestDto);

    ResponseAddressDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address toModelTemp(AddressRequestDto address);
}
