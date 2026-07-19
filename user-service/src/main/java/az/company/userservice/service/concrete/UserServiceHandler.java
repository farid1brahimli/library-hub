package az.company.userservice.service.concrete;

import az.company.userservice.dao.entity.UserEntity;
import az.company.userservice.dao.repository.UserRepository;
import az.company.userservice.exception.NotFoundException;
import az.company.userservice.mapper.UserMapper;
import az.company.userservice.model.enums.UserStatus;
import az.company.userservice.model.request.UpdateProfilRequest;
import az.company.userservice.model.response.BorrowHistoryResponse;
import az.company.userservice.model.response.UserResponse;
import az.company.userservice.service.abstraction.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.userservice.exception.enums.ErrorStatus.*;
import static az.company.userservice.mapper.UserMapper.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private @NonNull UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND.name(),
                        format(USER_NOT_FOUND.getMessage(), userId)
                )
        );
    }

    @Override
    public UserResponse getUserById(Long userId) {
        var entity = getUserEntity(userId);
        return userMapper.toResponse(entity);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    };

    @Override
    public UserResponse updateUserProfile(Long userId, UpdateProfilRequest updateProfilRequest) {
        var entity = getUserEntity(userId);
        entity.setEmail(updateProfilRequest.getEmail());
        entity.setFullName(updateProfilRequest.getFullName());
        userRepository.save(entity);
        return userMapper.toResponse(entity);
    };

    @Override
    public List<BorrowHistoryResponse> getUserBorrowHistory(Long userId) {
        var userEntity = getUserEntity(userId);
        return userEntity.getBorrowsHistory().stream()
                .map(borrowHistoryEntity -> BorrowHistoryResponse.builder()
                        .id(borrowHistoryEntity.getId())
                        .bookId(borrowHistoryEntity.getBookId())
                        .bookTitle(borrowHistoryEntity.getBookTitle())
                        .borrowedAt(borrowHistoryEntity.getBorrowedAt())
                        .returnedAt(borrowHistoryEntity.getReturnedAt())
                        .status(borrowHistoryEntity.getStatus())
                        .build())
                .toList();
    }

    @Override
    public void deleteUserById(Long userId) {
        var userEntity = getUserById(userId);
        userEntity.setStatus(UserStatus.INACTIVE);
    }
}
