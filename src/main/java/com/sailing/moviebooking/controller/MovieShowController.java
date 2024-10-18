package com.sailing.moviebooking.controller;

import com.sailing.moviebooking.dto.request.MovieShowCreationRequest;
import com.sailing.moviebooking.dto.response.ApiResponse;
import com.sailing.moviebooking.dto.response.MovieShowResponse;
import com.sailing.moviebooking.service.MovieShowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie-show")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieShowController {

    MovieShowService movieShowService;

    @PostMapping()
    public ApiResponse<MovieShowResponse> addMovieShow(
            @RequestBody MovieShowCreationRequest movieShowCreationRequest) {
        return ApiResponse.<MovieShowResponse>builder()
                .result(movieShowService.addMovieShow(movieShowCreationRequest)).build();
    }
}
