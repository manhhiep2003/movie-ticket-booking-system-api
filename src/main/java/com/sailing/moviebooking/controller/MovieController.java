package com.sailing.moviebooking.controller;

import com.sailing.moviebooking.dto.request.MovieCreationRequest;
import com.sailing.moviebooking.dto.response.ApiResponse;
import com.sailing.moviebooking.dto.response.MovieResponse;
import com.sailing.moviebooking.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieController {

    MovieService movieService;

    @PostMapping()
    public ApiResponse<MovieResponse> addMovie(@RequestBody MovieCreationRequest movieCreationRequest) {
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.addMovie(movieCreationRequest)).build();
    }

    @GetMapping()
    public ApiResponse<List<MovieResponse>> getMovies() {
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.getAllMovies()).build();
    }
}
