package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.MovieShowCreationRequest;
import com.sailing.moviebooking.dto.response.MovieShowResponse;
import com.sailing.moviebooking.model.MovieShow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieShowMapper {
    MovieShow toMovieShow(MovieShowCreationRequest movieShowCreationRequest);
    MovieShowResponse toMovieShowResponse(MovieShow movieShow);
}
