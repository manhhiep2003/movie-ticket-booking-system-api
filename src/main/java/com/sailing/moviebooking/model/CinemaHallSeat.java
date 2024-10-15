package com.sailing.moviebooking.model;

import com.sailing.moviebooking.constant.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaHallSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long CinemaHallSeatId;
    int seatRow;
    int seatCol;
    SeatType type;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    CinemaHall cinemaHall;
}
