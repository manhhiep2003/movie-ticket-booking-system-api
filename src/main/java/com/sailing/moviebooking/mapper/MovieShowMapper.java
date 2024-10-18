package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.MovieShowCreationRequest;
import com.sailing.moviebooking.dto.response.MovieShowResponse;
import com.sailing.moviebooking.model.MovieShow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieShowMapper {
    @Mapping(source = "title", target = "movie.title")
    MovieShow toMovieShow(MovieShowCreationRequest movieShowCreationRequest);
    @Mapping(source = "movie.title", target = "title")
    MovieShowResponse toMovieShowResponse(MovieShow movieShow);
}
