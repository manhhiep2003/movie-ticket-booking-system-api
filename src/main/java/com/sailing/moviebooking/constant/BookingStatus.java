package com.sailing.moviebooking.constant;

import lombok.Getter;

@Getter
public enum BookingStatus {
    REQUESTED,
    PENDING,
    CONFIRMED,
    CHECKED_IN,
    CANCELED,
    ABANDONED
}
