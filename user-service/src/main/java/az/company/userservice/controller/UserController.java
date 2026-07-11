package az.company.userservice.controller;

import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
