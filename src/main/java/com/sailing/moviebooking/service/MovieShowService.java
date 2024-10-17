package com.sailing.moviebooking.service;

import com.sailing.moviebooking.dto.request.MovieShowCreationRequest;
import com.sailing.moviebooking.dto.response.MovieShowResponse;
import com.sailing.moviebooking.mapper.MovieShowMapper;
import com.sailing.moviebooking.model.MovieShow;
import com.sailing.moviebooking.repository.MovieShowRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieShowService {

    MovieShowRepository movieShowRepository;
    MovieShowMapper movieShowMapper;

    public MovieShowResponse addMovieShow(MovieShowCreationRequest movieShowCreationRequest) {
        MovieShow movieShow = movieShowMapper.toMovieShow(movieShowCreationRequest);
        return movieShowMapper.toMovieShowResponse(movieShowRepository.save(movieShow));
    }
}
