package com.sailing.moviebooking.service;

import com.sailing.moviebooking.dto.request.UserCreationRequest;
import com.sailing.moviebooking.dto.request.UserUpdateRequest;
import com.sailing.moviebooking.dto.response.UserResponse;
import com.sailing.moviebooking.exception.AppException;
import com.sailing.moviebooking.exception.ErrorCode;
import com.sailing.moviebooking.mapper.UserMapper;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public User createUser(UserCreationRequest userCreationRequest) {
        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        userMapper.updateUser(user, userUpdateRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
