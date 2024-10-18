package com.sailing.moviebooking.model;

import com.sailing.moviebooking.constant.BookingStatus;
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
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String bookingNumber;
    int numberOfSeats;
    LocalDate creationDate;
    BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "showId")
    MovieShow movieShow;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @ManyToMany
    @JoinTable(
            name = "booking_seat",
            joinColumns = @JoinColumn(name = "booking_number"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    List<Seat> seats;
}
