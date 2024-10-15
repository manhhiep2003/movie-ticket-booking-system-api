package com.sailing.moviebooking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaHallResponse {
    int CinemaHallId;
    int totalSeats;
}
