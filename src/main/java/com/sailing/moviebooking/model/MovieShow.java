package com.sailing.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long showId;
    LocalDate creationDate;
    LocalDate startTime;
    LocalDate endTime;

    @ManyToOne
    @JoinColumn(name = "title")
    Movie movie;
}
