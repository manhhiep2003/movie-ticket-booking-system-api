package com.sailing.moviebooking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieShowResponse {
    LocalDate creationDate;
    LocalDate startTime;
    LocalDate endTime;
}
