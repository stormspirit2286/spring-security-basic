package vn.duynv.springsecuritybasic.service;

import vn.duynv.springsecuritybasic.dto.request.CreateUserRequest;
import vn.duynv.springsecuritybasic.dto.response.UserResponseDTO;
import vn.duynv.springsecuritybasic.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequest createUserRequest);
    List<UserResponseDTO> getUsers();
    UserResponseDTO getUserById(Long id);
    User getUserByUsername(String username);
    UserResponseDTO getCurrentUserInfo();
//    PagedResponseDTO<UserResponseDTO> getAllUsers(int page, int size);
//    UserResponseDTO updateProfile(UpdateProfileRequest updateRequest);
    void changePassword(String currentPassword, String newPassword);
    void deleteAccount(String password);
}
