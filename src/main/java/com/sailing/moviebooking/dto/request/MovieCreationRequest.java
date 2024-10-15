package com.sailing.moviebooking.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieCreationRequest {
    String title;
    String description;
    String genre;
    String director;
    String language;
    int durationInMin;
    LocalDate releaseDate;
}
