package org.globaroman.petshopba.mapper;

import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.ordercart.CreateOrderRequestDto;
import org.globaroman.petshopba.dto.ordercart.ResponseAddressDto;
import org.globaroman.petshopba.model.cartorder.Address;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {

    Address toEntity(CreateOrderRequestDto createOrderRequestDto);

    ResponseAddressDto toDto(Address address);
}
