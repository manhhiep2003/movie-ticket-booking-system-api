package com.sailing.moviebooking.repository;

import com.sailing.moviebooking.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieShowRepository extends JpaRepository<MovieShow, Integer> {
}
