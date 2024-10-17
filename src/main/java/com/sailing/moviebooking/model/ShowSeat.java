package com.sailing.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int showSeatId;
    int seatNumber;
    boolean isReverse;
    double price;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    CinemaHallSeat cinemaHallSeat;

    @ManyToOne
    @JoinColumn(name = "movie_show_id", nullable = false)
    MovieShow movieShow;
}
