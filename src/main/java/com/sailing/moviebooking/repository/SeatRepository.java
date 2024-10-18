package com.sailing.moviebooking.repository;

import com.sailing.moviebooking.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Cinema, String> {
}
