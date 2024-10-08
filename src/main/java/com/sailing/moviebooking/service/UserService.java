package com.sailing.moviebooking.service;

import com.sailing.moviebooking.dto.request.UserCreationRequest;
import com.sailing.moviebooking.dto.request.UserUpdateRequest;
import com.sailing.moviebooking.dto.response.UserResponse;
import com.sailing.moviebooking.exception.AppException;
import com.sailing.moviebooking.exception.ErrorCode;
import com.sailing.moviebooking.mapper.UserMapper;
import com.sailing.moviebooking.model.RoleEnum;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.repository.RoleRepository;
import com.sailing.moviebooking.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(RoleEnum.USER.name());
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasAuthority('CREATE_MOVIE')")
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponse(users);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST)));
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXIST));
        userMapper.updateUser(user, userUpdateRequest);
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getUserInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXIST));
        return userMapper.toUserResponse(user);
    }
}
