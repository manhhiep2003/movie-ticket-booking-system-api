package com.sailing.moviebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class City {
    @Id
    String cityName;
    String state;

    @OneToMany(mappedBy = "city")
    List<Cinema> cinemas;
}
