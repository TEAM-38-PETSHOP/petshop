package org.globaroman.petshopba.service.impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.RegistrationException;
import org.globaroman.petshopba.mapper.UserMapper;
import org.globaroman.petshopba.model.user.Role;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.RoleRepository;
import org.globaroman.petshopba.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Authentication authentication;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        authentication = new org.springframework.security
                .authentication.UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    @DisplayName("Register user -> Successful return UserResponseDto")
    void register_RegisterValidUser_ReturnUserResponseDto() throws RegistrationException {

        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");
        requestDto.setRepeatPassword("password");
        requestDto.setFirstName("David");
        requestDto.setLastName("Beckham");

        Mockito.when(userMapper.toModel(requestDto)).thenReturn(new User());

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            savedUser.setEmail("test@example.com"); // Установим email вручную
            return savedUser;
        });

        Mockito.when(userMapper.toDto(Mockito.any(User.class))).thenReturn(new UserResponseDto());

        Role mockRole = new Role();
        mockRole.setId(1L);
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));

        UserResponseDto result = userService.register(requestDto);

        Assertions.assertNotNull(result);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userMapper, Mockito.times(1)).toDto(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Update role user -> Successful return UserResponseDto with uprate role")
    @Transactional
    void update_ValidIdAndRole_ReturnsUserResponseDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Role.RoleName roleName = Role.RoleName.USER;
        Role mockRole = new Role();
        mockRole.setId(1L);

        UpdateRoleDto updateRoleDto = new UpdateRoleDto(roleName);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    savedUser.setId(1L);
                    return savedUser;
                });

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));

        Mockito.when(userMapper.toDto(Mockito.any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(savedUser.getId());
            return userResponseDto;
        });

        UserResponseDto result = userService.update(1L, updateRoleDto);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Get all users -> received List<Users> successful")
    void getAll_GetAllUsers_returnListUserResponseDtosOk() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(new UserResponseDto());
        responseDtos.add(new UserResponseDto());
        Mockito.when(userMapper.toDto(Mockito.any(User.class))).thenReturn(new UserResponseDto());

        List<UserResponseDto> userResponseDtos = userService.getAll();

        Assertions.assertEquals(2, userResponseDtos.size());
    }

    @Test
    @DisplayName("Delete user by themselves -> return successful delete")
    void deleteById_UserDeletesSelf_ReturnsSuccessfulDelete() {
        User user = new User();
        user.setId(1L);

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        userService.deleteById(1L, authentication);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("User tries to delete another user -> throws exception")
    void deleteById_UserTriesToDeleteAnotherUser_ThrowsException() {
        User user = new User();
        user.setId(1L);

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        Long anotherUserId = 2L;

        Assertions.assertThrows(RuntimeException.class, () ->
                        userService.deleteById(anotherUserId, authentication),
                "You do not have permission to delete this user.");

        Mockito.verify(userRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
