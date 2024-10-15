package com.sailing.moviebooking.service;

import com.sailing.moviebooking.dto.request.CinemaCreationRequest;
import com.sailing.moviebooking.dto.response.CinemaResponse;
import com.sailing.moviebooking.mapper.CinemaMapper;
import com.sailing.moviebooking.repository.CinemaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaService {

    CinemaRepository cinemaRepository;
    CinemaMapper cinemaMapper;

    public CinemaResponse addCinema(CinemaCreationRequest cinemaCreationRequest) {
        var cinema = cinemaMapper.toCinema(cinemaCreationRequest);
        return cinemaMapper.toCinemaResponse(cinemaRepository.save(cinema));
    }

    public List<CinemaResponse> getAllCinemas() {
        return cinemaRepository.findAll().stream().map(cinemaMapper::toCinemaResponse).toList();
    }

}
