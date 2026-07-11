package az.company.userservice.service;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.dao.repository.UserRepository;
import az.company.userservice.exception.UserPresentException;
import az.company.userservice.exception.enums.ErrorStatus;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.request.LoginRequest;
import az.company.userservice.model.response.LoginResponse;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static az.company.userservice.exception.enums.ErrorStatus.*;
import static az.company.userservice.mapper.UserMapper.*;
import static az.company.userservice.model.enums.UserRoles.ADMIN;
import static az.company.userservice.model.enums.UserRoles.USER;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenStorageService tokenStorageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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
        String accesToken = jwtService.generateAccesToken(userPrincipal);
        tokenStorageService.storeAccessToken(userPrincipal.getUsername(), accesToken);
        return LoginResponse
                .builder()
                .accessToken(accesToken)
                .build();


    }

}