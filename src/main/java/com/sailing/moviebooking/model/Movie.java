package com.sailing.moviebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {
    @Id
    String title;
    String description;
    String genre;
    String director;
    String language;
    int durationInMin;
    LocalDate releaseDate;

    @OneToMany(mappedBy = "movie")
    List<MovieShow> movieShows;
}
