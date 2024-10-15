package com.sailing.moviebooking.controller;

import com.sailing.moviebooking.dto.request.CinemaCreationRequest;
import com.sailing.moviebooking.dto.response.ApiResponse;
import com.sailing.moviebooking.dto.response.CinemaResponse;
import com.sailing.moviebooking.service.CinemaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaController {

    CinemaService cinemaService;

    @PostMapping()
    public ApiResponse<CinemaResponse> addCinema(@RequestBody CinemaCreationRequest cinemaCreationRequest) {
        return ApiResponse.<CinemaResponse>builder()
                .result(cinemaService.addCinema(cinemaCreationRequest)).build();
    }

    @GetMapping()
    public ApiResponse<List<CinemaResponse>> getCinemas() {
        return ApiResponse.<List<CinemaResponse>>builder()
                .result(cinemaService.getAllCinemas()).build();
    }
}
