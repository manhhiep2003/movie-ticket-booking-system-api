package com.sailing.moviebooking.model;

import jakarta.persistence.*;
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
public class MovieShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long movieShowId;
    LocalDate creationDate;
    LocalDate startTime;
    LocalDate endTime;

    @ManyToOne
    @JoinColumn(name = "title")
    Movie movie;

    @OneToMany(mappedBy = "movieShow")
    List<Booking> bookings;

    @OneToMany(mappedBy = "movieShow")
    List<ShowSeat> showSeats;
}
