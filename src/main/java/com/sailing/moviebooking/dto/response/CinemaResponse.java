package com.sailing.moviebooking.dto.response;

import com.sailing.moviebooking.model.CinemaHall;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaResponse {
    String cinemaName;
    String cinemaAddress;
    int totalCinemaHalls;
    List<CinemaHall> cinemaHalls;
}
