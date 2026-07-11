package az.company.userservice.controller;

import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.request.LoginRequest;
import az.company.userservice.model.response.LoginResponse;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}