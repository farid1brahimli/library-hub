package az.company.userservice.service.abstraction;

import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.request.LoginRequest;
import az.company.userservice.model.request.RefreshTokenRequest;
import az.company.userservice.model.response.LoginResponse;
import az.company.userservice.model.response.UserResponse;

public interface AuthService {
    UserResponse registerUser(CreateUserRequest createUserRequest);
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refresh(RefreshTokenRequest request);
    void logout(String username);
    void checkUsersLastLogin();

}
