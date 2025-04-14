package vn.duynv.springsecuritybasic.service;

import vn.duynv.springsecuritybasic.dto.request.CreateUserRequest;
import vn.duynv.springsecuritybasic.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequest createUserRequest);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getCurrentUserInfo();
//    PagedResponseDTO<UserResponseDTO> getAllUsers(int page, int size);
//    UserResponseDTO updateProfile(UpdateProfileRequest updateRequest);
    void changePassword(String currentPassword, String newPassword);
    void deleteAccount(String password);
}
