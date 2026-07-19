package az.company.userservice.service.abstraction;

import az.company.userservice.model.request.UpdateProfilRequest;
import az.company.userservice.model.response.BorrowHistoryResponse;
import az.company.userservice.model.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long userId);
    List<UserResponse> getAllUsers();
    void deleteUserById(Long userId);
    List<BorrowHistoryResponse> getUserBorrowHistory(Long userId);
    UserResponse updateUserProfile(Long userId, UpdateProfilRequest updateProfilRequest);
}
