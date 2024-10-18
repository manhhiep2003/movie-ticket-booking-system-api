package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.BookingRequest;
import com.sailing.moviebooking.dto.response.BookingResponse;
import com.sailing.moviebooking.model.Booking;
import com.sailing.moviebooking.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "movieShowId", target = "movieShow.movieShowId")
    @Mapping(source = "seatId", target = "seats", qualifiedByName = "mapSeatFromId")
    Booking toBooking(BookingRequest bookingRequest);
    BookingResponse toBookingResponse(Booking booking);

    @Named("mapSeatFromId")
    default List<Seat> mapSeatFromId(List<Integer> seatIds) {
        return seatIds.stream().map(seatId -> {
            Seat showSeat = new Seat();
            showSeat.setSeatId(seatId);
            return showSeat;
        }).collect(Collectors.toList());
    }
}
