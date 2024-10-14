package com.sailing.moviebooking.controller;

import com.nimbusds.jose.JOSEException;
import com.sailing.moviebooking.dto.request.AuthenticationRequest;
import com.sailing.moviebooking.dto.request.IntrospectRequest;
import com.sailing.moviebooking.dto.request.LogoutRequest;
import com.sailing.moviebooking.dto.request.RefreshTokenRequest;
import com.sailing.moviebooking.dto.response.ApiResponse;
import com.sailing.moviebooking.dto.response.AuthenticationResponse;
import com.sailing.moviebooking.dto.response.IntrospectResponse;
import com.sailing.moviebooking.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/outbound/authentication")
    ApiResponse<AuthenticationResponse> outboundAuthenticate(@RequestParam("code") String code) {
        var result = authenticationService.outboundAuthenticate(code);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest)
            throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest)
             throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshTokenRequest);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
