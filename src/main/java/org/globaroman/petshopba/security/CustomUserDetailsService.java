package org.globaroman.petshopba.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.*;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.error("Can't find user by email");
                    throw new EntityNotFoundCustomException("Can't find user by email");
                }
        );
    }
}
