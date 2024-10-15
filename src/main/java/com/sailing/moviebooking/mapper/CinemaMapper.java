package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.CinemaCreationRequest;
import com.sailing.moviebooking.dto.response.CinemaResponse;
import com.sailing.moviebooking.model.Cinema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema toCinema(CinemaCreationRequest cinemaCreationRequest);
    CinemaResponse toCinemaResponse(Cinema cinema);
}
