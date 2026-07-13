package az.company.userservice.service;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.dao.repository.UserRepository;
import az.company.userservice.exception.UserNotFoundException;
import az.company.userservice.exception.UserPresentException;
import az.company.userservice.exception.enums.ErrorStatus;
import az.company.userservice.model.enums.UserStatus;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.request.LoginRequest;
import az.company.userservice.model.response.LoginResponse;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

import static az.company.userservice.exception.enums.ErrorStatus.*;
import static az.company.userservice.mapper.UserMapper.*;
import static az.company.userservice.model.enums.UserRoles.ADMIN;
import static az.company.userservice.model.enums.UserRoles.USER;
import static az.company.userservice.model.enums.UserStatus.INACTIVE;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
//    private final UserEntity userEntity;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenStorageService tokenStorageService;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(CreateUserRequest createUserRequest) {
        if (userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
            throw new UserPresentException(
                    ErrorStatus.USER_ALREADY_EXISTS.name(),
                    format(USER_ALREADY_EXISTS.getMessage(), createUserRequest.getUsername())
            );
        }
        var userEntity = mapToUserEntity(createUserRequest);
        userEntity.setPassword(
                passwordEncoder.encode(createUserRequest.getPassword())
        );
        if (userRepository.countByUsers() == 0) {
            userEntity.setRoles(Set.of(ADMIN, USER));
        }
        userRepository.save(userEntity);
        return mapToUserResponse(userEntity);

    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtService.generateAccesToken(userPrincipal);
        tokenStorageService.storeAccessToken(userPrincipal.getUsername(), accessToken);
        UserEntity userEntity =  userRepository.findByUsername(userPrincipal.getUsername()).orElseThrow(
                () -> new UserNotFoundException(
                        USER_NOT_FOUND.name(),
                        format(USER_NOT_FOUND.getMessage(),userPrincipal.getId()))

        );
        userEntity.setLastLoginAt(LocalDateTime.now());
        return LoginResponse
                .builder()
                .accessToken(accessToken)
                .build();


    }

    @Scheduled(cron = "*/10 * * * * *")
    public void checkUsersLastLogin(){
        var List = userRepository.findAll().stream()
                .filter(user -> user.getLastLoginAt()
                        .isBefore(LocalDateTime.now()
                                .minusDays(90))).toList();

        for ( UserEntity userEntity : List) {
            userEntity.setStatus(INACTIVE);
        }
    }

}