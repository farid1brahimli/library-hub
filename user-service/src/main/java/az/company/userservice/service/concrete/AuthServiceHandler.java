package az.company.userservice.service.concrete;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.dao.repository.UserRepository;
import az.company.userservice.exception.NotFoundException;
import az.company.userservice.exception.TokenInvalidationException;
import az.company.userservice.exception.UserPresentException;
import az.company.userservice.exception.enums.ErrorStatus;
import az.company.userservice.mapper.UserMapper;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.request.LoginRequest;
import az.company.userservice.model.request.RefreshTokenRequest;
import az.company.userservice.model.response.LoginResponse;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.security.UserPrincipal;
import az.company.userservice.security.service.JwtService;
import az.company.userservice.security.service.TokenStorageService;
import az.company.userservice.service.abstraction.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

import static az.company.userservice.exception.enums.ErrorStatus.*;
import static az.company.userservice.model.enums.UserRoles.ADMIN;
import static az.company.userservice.model.enums.UserRoles.USER;
import static az.company.userservice.model.enums.UserStatus.INACTIVE;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceHandler implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenStorageService tokenStorageService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;

    @Override
    public UserResponse registerUser(CreateUserRequest createUserRequest) {
        if (userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
            throw new UserPresentException(
                    ErrorStatus.USER_ALREADY_EXISTS.name(),
                    format(USER_ALREADY_EXISTS.getMessage(), createUserRequest.getUsername())
            );
        }
        var userEntity = userMapper.toEntity(createUserRequest);
        userEntity.setPassword(
                passwordEncoder.encode(createUserRequest.getPassword())
        );
        if (userRepository.countByUsers() == 0) {
            userEntity.setRoles(Set.of(ADMIN, USER));
        }
        userRepository.save(userEntity);
        return userMapper.toResponse(userEntity);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userPrincipal);
        String refreshToken = jwtService.generateRefreshToken(userPrincipal);
        tokenStorageService.storeAccessToken(userPrincipal.getUsername(), accessToken);
        tokenStorageService.storeRefreshToken(userPrincipal.getUsername(), refreshToken);
        UserEntity userEntity =  userRepository.findById(userPrincipal.getId()).orElseThrow(
                () -> new NotFoundException(
                        USER_NOT_FOUND.name(),
                        format(USER_NOT_FOUND.getMessage(),userPrincipal.getUsername()))

        );
        userEntity.setLastLoginAt(LocalDateTime.now());
        return LoginResponse
                .builder()
                .accessToken(accessToken)
                .build();


    }

    @Override
    public LoginResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenValid(refreshToken) || !jwtService.isRefreshTokenValid(refreshToken)) {
            throw new TokenInvalidationException(
                    ErrorStatus.TOKEN_INVALID.name(),
                    format(TOKEN_INVALID.getMessage(), refreshToken)
            );
        }

        String username = jwtService.extractUsername(refreshToken);

        if (!tokenStorageService.isRefreshTokenValid(username, refreshToken)) {
            throw new TokenInvalidationException(
                    REFRESH_TOKEN_IS_NOT_ACTIVE.name(),
                    format(REFRESH_TOKEN_IS_NOT_ACTIVE.getMessage(), refreshToken)
            );
        }

        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtService.generateAccessToken(userPrincipal);
        String newRefreshToken = jwtService.generateRefreshToken(userPrincipal);

        tokenStorageService.storeAccessToken(username, newAccessToken);
        tokenStorageService.storeRefreshToken(username, newRefreshToken);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    public void logout(String username) {
        tokenStorageService.deleteAccessToken(username);
        tokenStorageService.deleteRefreshToken(username);
    }

    @Scheduled(cron = "0 0 0 * * MON")
    @Override
    public void checkUsersLastLogin(){
        var List = userRepository.findAll().stream()
                .filter(user -> user.getLastLoginAt()
                        .isBefore(LocalDateTime.now()
                                .minusDays(90))).toList();

        for ( UserEntity userEntity : List) {
            userEntity.setStatus(INACTIVE);
            log.info("INACTIVE USER: id: {}, username: {}, lastloginat: {}", userEntity.getId(), userEntity.getUsername(), userEntity.getLastLoginAt());

        }
    }

}