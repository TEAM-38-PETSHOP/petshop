package org.globaroman.petshopba.service.impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.controller.UserFeedbackDto;
import org.globaroman.petshopba.dto.user.CodeForNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.CreateNewPasswordRequestDto;
import org.globaroman.petshopba.dto.user.FeedbackMapper;
import org.globaroman.petshopba.dto.user.UpdateProfileUserRequestDto;
import org.globaroman.petshopba.dto.user.UpdateRoleDto;
import org.globaroman.petshopba.dto.user.UserEmailForRecovePasswordRequestDto;
import org.globaroman.petshopba.dto.user.UserRegistrationRequestDto;
import org.globaroman.petshopba.dto.user.UserResponseDto;
import org.globaroman.petshopba.exception.EntityNotFoundCustomException;
import org.globaroman.petshopba.exception.RegistrationException;
import org.globaroman.petshopba.exception.global.PasswordChangeException;
import org.globaroman.petshopba.mapper.UserMapper;
import org.globaroman.petshopba.model.cartorder.Order;
import org.globaroman.petshopba.model.cartorder.ShoppingCart;
import org.globaroman.petshopba.model.user.Feedback;
import org.globaroman.petshopba.model.user.RecoveryPasswordCode;
import org.globaroman.petshopba.model.user.Role;
import org.globaroman.petshopba.model.user.User;
import org.globaroman.petshopba.repository.FeedBackRepository;
import org.globaroman.petshopba.repository.OrderRepository;
import org.globaroman.petshopba.repository.RecoveryCodeRepository;
import org.globaroman.petshopba.repository.RoleRepository;
import org.globaroman.petshopba.repository.ShoppingCartRepository;
import org.globaroman.petshopba.repository.UserRepository;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.globaroman.petshopba.service.EmailSenderService;
import org.globaroman.petshopba.service.TransliterationService;
import org.globaroman.petshopba.service.UploadImageService;
import org.globaroman.petshopba.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private static final Long USER_ROLE_ID =
            (long) Role.RoleName.USER.ordinal() + 1;
    private static final int CODE_SIZE = 6;

    @Value("${mail.login}")
    private String hostEmail;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final RecoveryCodeRepository recoveryCodeRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final AmazonS3Service amazonS3Service;
    private final UploadImageService uploadImageService;
    private final FeedBackRepository feedBackRepository;
    private final TransliterationService transliterationService;
    private final FeedbackMapper feedbackMapper;

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
    public String deleteById(Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Role userRole = roleRepository.findByRole(Role.RoleName.ADMIN).orElseThrow(
                () -> {
                    log.error("No found role: " + Role.RoleName.ADMIN);
                    return new EntityNotFoundCustomException("No found role: "
                            + Role.RoleName.ADMIN);
                }
        );

        if (user.getRoles().contains(userRole)) {
            deleteOrder(id);
            userRepository.deleteById(id);
            return "User by id:" + id + " successfully deleted";
        }

        if (Objects.equals(user.getId(), id)) {
            deleteOrder(id);
            userRepository.deleteById(id);
            return "User by id:" + id + " successfully deleted";
        }

        throw new RuntimeException("You do not have permission to delete this user.");
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
                    return new EntityNotFoundCustomException("No found user by email: "
                            + requestDto.email());
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
        Optional<RecoveryPasswordCode> codeOptional = recoveryCodeRepository
                .findByCode(requestDto.code());
        if (codeOptional.isEmpty()) {
            return false;
        }

        RecoveryPasswordCode recoveryPasswordCode = codeOptional.get();
        User user = userRepository.findByEmail(recoveryPasswordCode.getEmail()).orElseThrow(() -> {
            log.error("No found user by email: " + recoveryPasswordCode.getEmail());
            return new EntityNotFoundCustomException("No found user by email: "
                    + recoveryPasswordCode.getEmail());
        });

        user.setPassword(passwordEncoder.encode(requestDto.password()));
        userRepository.save(user);
        recoveryCodeRepository.delete(recoveryPasswordCode);

        return true;
    }

    @Override
    public UserResponseDto updateProfile(UpdateProfileUserRequestDto requestDto,
                                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPhone(requestDto.getPhone());

        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()) {

            boolean isPasswordMatch = passwordEncoder.matches(requestDto.getOldPassword(),
                    user.getPassword());
            if (!isPasswordMatch) {
                log.error(user.getId() + "Your password doesn't match the saved one. Try again");
                throw new PasswordChangeException(
                        "Your password doesn't match the saved one. Try again");
            }

            user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void handleFeedback(String message, String experience, MultipartFile[] files,
                               Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<String> fileUrls = new ArrayList<>();
        Feedback feedback = new Feedback();
        feedback.setFirstName(user.getFirstName());
        feedback.setLastName(user.getLastName());
        feedback.setEmail(user.getEmail());
        feedback.setPhone(user.getPhone());
        feedback.setExperience(experience);
        feedback.setMessage(message);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                fileUrls.add(amazonS3Service.uploadImageFromFile(file,
                            getStringObjectKey(file.getOriginalFilename())));
            }
        }

        feedback.setImageUrls(fileUrls);

        feedBackRepository.save(feedback);

        sendMessageToUserAfterFeedback(user, feedback.getId());
        sendMessageToManagement(user, feedback);
    }

    @Override
    public List<UserFeedbackDto> getAllFeedback() {
        return feedBackRepository.findAll().stream()
                .map(feedbackMapper::toDto)
                .toList();
    }

    private void sendMessageToManagement(User user, Feedback feedback) {
        String imageUrls = String.join(",\n", feedback.getImageUrls());

        String message = "Від: " + user.getEmail() + "\n"
                + user.getFirstName() + " " + user.getLastName() + "\n"
                + "Телефон: " + user.getPhone() + "\n"
                + "Повідомлення: " + feedback.getMessage() + "\n"
                + imageUrls;

        emailSenderService.sendEmail(
                user.getEmail(),
                hostEmail,
                "Feedback N#" + feedback.getId() + " щодо " + feedback.getExperience(),
                message);
    }

    private void sendMessageToUserAfterFeedback(User user, Long id) {
        emailSenderService.sendEmail("OneGroom.com.ua",
                user.getEmail(),
                "Дякуємо за ваше звернення!",

                "Шановний(а)" + user.getFirstName() + "!\n"
                        + "Дякуємо за те, що звернулися до нас! Ми отримали ваше повідомлення"
                        + " N " + id + " і вже працюємо над його розглядом.\n"
                        + "Наші фахівці ознайомляться з вашим зверненням та нададуть "
                        + "відповідь якнайшвидше."
                        + "У разі потреби додаткової інформації, ми зв'яжемося з вами за вказаними "
                        + "контактними даними.\n"
                        + "Якщо у вас є ще які-небудь питання або вам потрібна додаткова допомога, "
                        + "не соромтеся звертатися до нас.\n"
                        + "Дякуємо за довіру і співпрацю!\n"
                        + "З повагою, команда OneGroom!");
    }

    private void sendMessageToUser(User user, String code) {
        emailSenderService.sendEmail(
                    "OneGroom.com.ua",
                    user.getEmail(),
                    "Ваш код безпеки OneGroom",
                    "Вітаємо, " + user.getFirstName() + "!\n"
                            + "Ваш захисний код:\n\n"
                            + code + "\n\n"
                            + "Щоб підтвердити вашу особу у OneGroom, "
                            + "нам необхідно підтвердити вашу електронну адресу."
                            + "вашу електронну адресу. Вставте цей код у браузері. "
                            + "Його можна використати лише раз."
        );
    }

    private String createCodeForRecovery() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private User getUserWithRoleAndPasswordEncode(UserRegistrationRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(getRoleFromDB(USER_ROLE_ID)));

        return user;
    }

    private void deleteOrder(Long id) {
        Optional<ShoppingCart> carts = shoppingCartRepository.findByUserId(id);
        carts.ifPresent(shoppingCartRepository::delete);
        List<Order> orders = orderRepository.findByUserId(id);
        orders.forEach(order -> orderRepository.delete(order));
    }

    private Role getRoleFromDB(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Can't find role with id: " + id);
                    return new UsernameNotFoundException("Can't find role with id: " + id);
                });
    }

    private String getStringObjectKey(String nameProduct) {
        Random random = new Random();
        int nameImage = random.nextInt(10000);

        String latinLine = transliterationService.getLatinStringLine(nameProduct);

        return "_" + getNameProductShort(latinLine) + "_" + nameImage + ".jpg";

    }

    private String getNameProductShort(String name) {
        String[] splStr = name.split(" ");
        if (splStr.length > 3) {
            return splStr[0] + " " + splStr[1] + " " + splStr[2];
        } else {
            return name;
        }
    }
}
