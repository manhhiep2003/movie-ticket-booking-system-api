package com.sailing.moviebooking.dto.response;

import com.sailing.moviebooking.constant.BookingStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    String bookingNumber;
    int numberOfSeats;
    LocalDate creationDate;
    BookingStatus status;
}
