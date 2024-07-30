package com.sailing.moviebooking.controller;

import com.sailing.moviebooking.dto.request.UserCreationRequest;
import com.sailing.moviebooking.dto.request.UserUpdateRequest;
import com.sailing.moviebooking.dto.response.ApiResponse;
import com.sailing.moviebooking.dto.response.UserResponse;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    UserResponse getUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
                    @PathVariable("userId") String userId) {
        return userService.updateUser(userUpdateRequest, userId);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User deleted";
    }
}
