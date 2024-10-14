package com.sailing.moviebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieTicketBookingSystemApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketBookingSystemApiApplication.class, args);
    }

}
