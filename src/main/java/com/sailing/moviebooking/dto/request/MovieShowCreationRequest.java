package com.sailing.moviebooking.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieShowCreationRequest {
    LocalDate creationDate;
    LocalDate startTime;
    LocalDate endTime;
}
