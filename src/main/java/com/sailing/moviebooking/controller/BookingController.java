//package com.sailing.moviebooking.controller;
//
//import com.sailing.moviebooking.dto.request.BookingRequest;
//import com.sailing.moviebooking.dto.request.MovieCreationRequest;
//import com.sailing.moviebooking.dto.response.ApiResponse;
//import com.sailing.moviebooking.dto.response.BookingResponse;
//import com.sailing.moviebooking.dto.response.MovieResponse;
//import com.sailing.moviebooking.service.BookingService;
//import com.sailing.moviebooking.service.MovieService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/booking")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class BookingController {
//
//    BookingService bookingService;
//
//    @PostMapping()
//    public ApiResponse<BookingResponse> bookTicket(@RequestBody BookingRequest bookingRequest) {
//        return ApiResponse.<BookingResponse>builder()
//                .result(bookingService.bookTicket(bookingRequest)).build();
//    }
//}
