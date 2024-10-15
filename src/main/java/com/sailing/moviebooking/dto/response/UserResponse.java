package com.sailing.moviebooking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String username;
    String firstName;
    String lastName;
    boolean hasPassword;
    String email;
    LocalDate birthday;
    Set<RoleResponse> roles;
}
