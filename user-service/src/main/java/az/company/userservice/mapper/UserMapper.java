package az.company.userservice.mapper;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.model.enums.UserRoles;
import az.company.userservice.model.enums.UserStatus;
import az.company.userservice.model.request.CreateUserRequest;
import az.company.userservice.model.response.UserResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.Set;
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "borrowsHistory", ignore = true)
    UserEntity toEntity(CreateUserRequest request);

//    @Mapping(source = "roles", target = "roles")
    UserResponse toResponse(UserEntity entity);

    @AfterMapping
    default void setDefaults(@MappingTarget UserEntity userEntity) {
        userEntity.setRoles(Set.of(UserRoles.USER));
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
    }
}