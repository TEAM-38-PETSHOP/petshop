package org.globaroman.petshopba.mapper;

import java.util.Optional;
import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.model.user.UserTemp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    @Named("idFromUser")
    default Long idFromUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getId)
                .orElse(null);
    }

    @Named("phoneFromUser")
    default String phoneFromUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getPhone)
                .orElse(null);
    }

    @Named("idFromUserTemp")
    default Long idFromUserTemp(UserTemp userTemp) {
        return Optional.ofNullable(userTemp)
                .map(UserTemp::getId)
                .orElse(null);
    }
}
