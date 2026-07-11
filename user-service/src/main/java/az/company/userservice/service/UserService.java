package az.company.userservice.service;

import az.company.userservice.dao.repository.UserRepository;
import az.company.userservice.exception.UserNotFoundException;
import az.company.userservice.exception.UserPresentException;
import az.company.userservice.exception.enums.ErrorStatus;
import az.company.userservice.mapper.UserMapper;
import az.company.userservice.model.enums.UserRoles;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static az.company.userservice.exception.enums.ErrorStatus.*;
import static az.company.userservice.mapper.UserMapper.*;
import static az.company.userservice.model.enums.UserRoles.ADMIN;
import static az.company.userservice.model.enums.UserRoles.USER;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserById(Long userId) {
        var entity = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        USER_NOT_FOUND.name(),
                        format(USER_NOT_FOUND.getMessage(), userId))
        );

        return mapToUserResponse(entity);
    }
}
