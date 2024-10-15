package com.sailing.moviebooking.model;

import com.sailing.moviebooking.constant.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id
    String bookingNumber;
    int numberOfSeats;
    LocalDate creationDate;
    BookingStatus status;
}
