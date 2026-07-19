package az.company.userservice.controller;

import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.request.LoginRequest;
import az.company.userservice.model.request.RefreshTokenRequest;
import az.company.userservice.model.response.LoginResponse;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.service.abstraction.AuthService;
import az.company.userservice.service.concrete.AuthServiceHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public UserResponse registerUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return authService.registerUser(createUserRequest);
    }
    @PostMapping("/login")
    @ResponseStatus(CREATED)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @PostMapping("/refresh")
    @ResponseStatus(CREATED)
    public LoginResponse refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }

    @DeleteMapping("/{userName}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable @AuthenticationPrincipal String userName) {
        authService.logout(userName);
    }
}