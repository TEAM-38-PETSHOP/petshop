package org.globaroman.petshopba.mapper;

import java.util.Optional;
import org.globaroman.petshopba.config.MapperConfig;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);

    @Named("idFromUser")
    default Long idFromUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getId)
                .orElse(null);
    }
}
