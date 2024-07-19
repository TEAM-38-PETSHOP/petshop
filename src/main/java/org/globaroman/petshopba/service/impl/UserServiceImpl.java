package org.globaroman.petshopba.service.impl;

import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.dto.user.CodeForNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.CreateNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserEmailForRecovePasswordRequestDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.exception.RegistrationException;
import org.globaroman.petshopba.mapper.UserMapper;
import org.globaroman.petshopba.model.user.RecoveryPasswordCode;
import org.globaroman.petshopba.model.user.Role;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.RecoveryCodeRepository;
import org.globaroman.petshopba.repository.RoleRepository;
import org.globaroman.petshopba.repository.UserRepository;
import org.globaroman.petshopba.service.EmailSenderService;
import org.globaroman.petshopba.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private static final Long USER_ROLE_ID =
            (long) Role.RoleName.USER.ordinal() + 1;
    private static final int CODE_SIZE = 6;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RecoveryCodeRepository recoveryCodeRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            log.error("Can't register user. The same user with email "
                    + requestDto.getEmail()
                    + " already exist");
            throw new RegistrationException(
                    "Can't register user. The same user with email "
                            + requestDto.getEmail()
                            + " already exist");
        }
        User user = getUserWithRoleAndPasswordEncode(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UpdateRoleDto updateRoleDto) {
        Role.RoleName roleName = Role.RoleName.valueOf(updateRoleDto.getRole().name());
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error("No found user by id: " + id);
                    return new EntityNotFoundCustomException("No found user by id: " + id);
                }
        );

        Role existRole = getRoleFromDB((long)roleName.ordinal() + 1);

        Set<Role> roles = new HashSet<>();
        roles.add(existRole);
        user.setRoles(roles);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userMapper.toDto(user);
    }

    @Override
    public boolean getCodeForNewPassword(UserEmailForRecovePasswordRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.email()).orElseThrow(
                () -> {
                    log.error("No found user by email: " + requestDto.email());
                    return new EntityNotFoundCustomException("No found user by email: " + requestDto.email());
                }
        );

        String code = createCodeForRecovery();

        RecoveryPasswordCode recoveryPasswordCode = new RecoveryPasswordCode();
        recoveryPasswordCode.setEmail(user.getEmail());
        recoveryPasswordCode.setCode(code);
        recoveryCodeRepository.save(recoveryPasswordCode);

        CodeForNewPasswordRequestDto responseDto = new CodeForNewPasswordRequestDto(code);

        sendMessageToUser(user, code);

        return true;
    }

    @Override
    public boolean compareCode(CodeForNewPasswordRequestDto requestDto) {
        Optional<RecoveryPasswordCode> code = recoveryCodeRepository.findByCode(requestDto.code());
        return code.isPresent();
    }

    @Override
    public boolean resetPassword(CreateNewPasswordRequestDto requestDto) {
        Optional<RecoveryPasswordCode> codeOptional = recoveryCodeRepository.findByCode(requestDto.code());
        if (codeOptional.isEmpty()) {
            return false;
        }

        RecoveryPasswordCode recoveryPasswordCode = codeOptional.get();
        User user = userRepository.findByEmail(recoveryPasswordCode.getEmail()).orElseThrow(() -> {
            log.error("No found user by email: " + recoveryPasswordCode.getEmail());
            return new EntityNotFoundCustomException("No found user by email: " + recoveryPasswordCode.getEmail());
        });

        user.setPassword(passwordEncoder.encode(requestDto.password()));
        userRepository.save(user);
        recoveryCodeRepository.delete(recoveryPasswordCode);

        return true;
    }


    private void sendMessageToUser(User user, String code) {
            emailSenderService.sendEmail(
                    "OneGroom.com.ua",
                    user.getEmail(),
                    "Ваш код безпеки OneGroom",
                    "Вітаємо, " + user.getFirstName() + "!\n"
                            + "Ваш захисний код:\n\n"
                            + code + "\n\n"
                            + "Щоб підтвердити вашу особу у OneGroom, нам необхідно підтвердити вашу електронну адресу."
                            + " Вставте цей код у браузері. Його можна використати лише раз."
            );
        }


    private String createCodeForRecovery() {
        return String.format("%06d", new Random().nextInt(999999));}

    private User getUserWithRoleAndPasswordEncode(UserRegistrationRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(getRoleFromDB(USER_ROLE_ID)));

        return user;
    }

    private Role getRoleFromDB(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Can't find role with id: " + id);
                    return new UsernameNotFoundException("Can't find role with id: " + id);
                });
    }
}
