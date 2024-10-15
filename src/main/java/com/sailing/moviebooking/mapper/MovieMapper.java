package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.MovieCreationRequest;
import com.sailing.moviebooking.dto.response.MovieResponse;
import com.sailing.moviebooking.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toMovie(MovieCreationRequest movieCreationRequest);
    MovieResponse toMovieResponse(Movie movie);
}
