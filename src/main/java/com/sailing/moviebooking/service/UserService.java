package com.sailing.moviebooking.service;

import com.sailing.moviebooking.dto.request.UserCreationRequest;
import com.sailing.moviebooking.dto.request.UserUpdateRequest;
import com.sailing.moviebooking.exception.AppException;
import com.sailing.moviebooking.exception.ErrorCode;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest userCreationRequest) {
        User user = new User();
        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(userCreationRequest.getPassword());
        user.setEmail(userCreationRequest.getEmail());
        user.setFirstName(userCreationRequest.getFirstName());
        user.setLastName(userCreationRequest.getLastName());
        user.setBirthday(userCreationRequest.getBirthday());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UserUpdateRequest userUpdateRequest, String userId) {
        User user = getUserById(userId);
        user.setPassword(userUpdateRequest.getPassword());
        user.setEmail(userUpdateRequest.getEmail());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setBirthday(userUpdateRequest.getBirthday());
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
