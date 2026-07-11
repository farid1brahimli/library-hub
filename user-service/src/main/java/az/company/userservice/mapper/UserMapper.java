package az.company.userservice.mapper;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.response.UserResponse;

import java.util.Set;

import static az.company.userservice.model.enums.UserRoles.USER;
import static az.company.userservice.model.enums.UserStatus.ACTIVE;

public class UserMapper {
    public static UserEntity mapToUserEntity(CreateUserRequest createUserRequest) {
        return UserEntity.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .email(createUserRequest.getEmail())
                .fullName(createUserRequest.getFullName())
                .roles(Set.of(USER))
                .status(ACTIVE)
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build();
    }

    public static UserResponse mapToUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .fullName(userEntity.getFullName())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
