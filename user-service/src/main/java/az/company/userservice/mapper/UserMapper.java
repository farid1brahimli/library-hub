package az.company.userservice.mapper;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;

public class UserMapper {

    public static UserEntity mapToUserEntity(CreateUserRequest createUserRequest) {
        return UserEntity.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .fullName(createUserRequest.getFullName())
                .build();
    }

    public static UserResponse mapToUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .fullName(userEntity.getFullName())
                .role(userEntity.getRole())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
