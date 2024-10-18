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
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seatId;
    boolean isReverse;
    double price;
    SeatType type;

    @ManyToOne
    @JoinColumn(name = "movie_show_id", nullable = false)
    MovieShow movieShow;
}
