package com.sailing.moviebooking.service;

import com.sailing.moviebooking.constant.PredefinedRole;
import com.sailing.moviebooking.dto.request.PasswordCreationRequest;
import com.sailing.moviebooking.dto.request.UserCreationRequest;
import com.sailing.moviebooking.dto.request.UserUpdateRequest;
import com.sailing.moviebooking.dto.response.UserResponse;
import com.sailing.moviebooking.exception.AppException;
import com.sailing.moviebooking.exception.ErrorCode;
import com.sailing.moviebooking.mapper.UserMapper;
import com.sailing.moviebooking.model.Role;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.repository.RoleRepository;
import com.sailing.moviebooking.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

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
        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void createPassword(PasswordCreationRequest passwordCreationRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXIST));
        if (StringUtils.hasText(user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(passwordCreationRequest.getPassword()));
        userRepository.save(user);
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

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getUserInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXIST));
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setHasPassword(StringUtils.hasText(user.getPassword()));
        return userResponse;
    }
}
