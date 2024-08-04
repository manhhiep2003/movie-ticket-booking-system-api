package com.sailing.moviebooking.dto.request;

import com.sailing.moviebooking.validation.BirthdayValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 5, message = "INVALID_USERNAME")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    String firstName;

    String lastName;

    @Email
    String email;

    @BirthdayValidation(min = 13, message = "INVALID_BIRTHDAY")
    LocalDate birthday;
}
