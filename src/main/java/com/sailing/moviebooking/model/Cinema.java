package com.sailing.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cinema {
    @Id
    String cinemaName;
    String cinemaAddress;
    int totalCinemaHalls;

    @OneToMany(mappedBy = "cinema")
    List<CinemaHall> cinemaHalls;

    @ManyToOne
    @JoinColumn(name = "cityName")
    City city;

    @OneToMany(mappedBy = "cinema")
    List<MovieShow> movieShows;
}
