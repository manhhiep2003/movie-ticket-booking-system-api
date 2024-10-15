package com.sailing.moviebooking.service;

import com.sailing.moviebooking.dto.request.MovieCreationRequest;
import com.sailing.moviebooking.dto.response.MovieResponse;
import com.sailing.moviebooking.mapper.MovieMapper;
import com.sailing.moviebooking.repository.MovieRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieService {

    MovieRepository movieRepository;
    MovieMapper movieMapper;

    public MovieResponse addMovie(MovieCreationRequest movieCreationRequest) {
        var movie = movieMapper.toMovie(movieCreationRequest);
        return movieMapper.toMovieResponse(movieRepository.save(movie));
    }

    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream().map(movieMapper::toMovieResponse).toList();
    }

}
