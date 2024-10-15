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
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int CinemaHallId;
    int totalSeats;

    @ManyToOne
    @JoinColumn(name = "cinema_name")
    Cinema cinema;

    @OneToMany(mappedBy = "cinemaHall")
    List<CinemaHallSeat> cinemaHallSeats;
}
