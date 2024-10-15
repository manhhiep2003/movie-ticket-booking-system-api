package com.sailing.moviebooking.dto.request;

import com.sailing.moviebooking.model.CinemaHall;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaCreationRequest {
    String cinemaName;
    String cinemaAddress;
    int totalCinemaHalls;
    List<CinemaHall> cinemaHalls;
}
