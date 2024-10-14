package com.sailing.moviebooking.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.sailing.moviebooking.constant.PredefinedRole;
import com.sailing.moviebooking.dto.request.*;
import com.sailing.moviebooking.dto.response.AuthenticationResponse;
import com.sailing.moviebooking.dto.response.IntrospectResponse;
import com.sailing.moviebooking.exception.AppException;
import com.sailing.moviebooking.exception.ErrorCode;
import com.sailing.moviebooking.model.InvalidatedToken;
import com.sailing.moviebooking.model.Role;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.repository.InvalidatedTokenRepository;
import com.sailing.moviebooking.repository.httpclient.OutboundIdentityClient;
import com.sailing.moviebooking.repository.UserRepository;
import com.sailing.moviebooking.repository.httpclient.OutboundUserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    OutboundIdentityClient outboundIdentityClient;
    OutboundUserClient outboundUserClient;

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${outbound.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        log.info("SignKey: {}", SIGNER_KEY);
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches
                (authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).authenticated(true).build();
    }

    public AuthenticationResponse outboundAuthenticate(String code) {
        var response = outboundIdentityClient.exchangeToken(
                ExchangeTokenRequest.builder()
                        .code(code)
                        .clientId(CLIENT_ID)
                        .clientSecret(CLIENT_SECRET)
                        .redirectUri(REDIRECT_URI)
                        .grantType(GRANT_TYPE)
                .build());
        log.info("Token response: {}", response);
        var userInfo = outboundUserClient.getUserInfo("json", response.getAccessToken());
        log.info("User info: {}", userInfo);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().roleName(PredefinedRole.USER_ROLE).build());
        var user = userRepository.findByUsername(userInfo.getEmail()).orElseGet(()
                -> userRepository.save(User.builder()
                        .username(userInfo.getEmail())
                        .firstName(userInfo.getGivenName())
                        .lastName(userInfo.getFamilyName())
                        .roles(roles)
                .build()));
        return AuthenticationResponse.builder().token(response.getAccessToken()).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("sailing.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        log.info("Generating token for user: {}", user.getUsername());
        log.info("Token claims: {}", jwtClaimsSet.toJSONObject());
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
            throws ParseException, JOSEException {
        log.info("Refreshing token: {}", refreshTokenRequest.getToken());
        var signJWT = verifyToken(refreshTokenRequest.getToken(), true);
        var jti = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
        log.info("Token ID (jti): {}", jti);
        log.info("Token expiry time: {}", expiryTime);
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryDate(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        var username = signJWT.getJWTClaimsSet().getSubject();
        log.info("Refreshing token for user: {}", username);
        var user = userRepository.findByUsername(username).orElseThrow(() ->
                new AppException(ErrorCode.UNAUTHENTICATED));
        var token = generateToken(user);
        log.info("Generated new token: {}", token);
        return AuthenticationResponse.builder()
                .token(token).authenticated(true).build();

    }

    public void logout(LogoutRequest logoutRequest)
            throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(logoutRequest.getToken(), true);
            String jti = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jti)
                    .expiryDate(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException appException) {
            log.info("Token already expired");
        }

    }

    private SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {
        log.info("Verifying token: {}", token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        log.info("Token expiration time: {}", expiryTime);
        var verified = signedJWT.verify(verifier);
        log.info("Token verification result: {}", verified);
        if (!(verified && expiryTime.after(new Date()))) {
            log.error("Token verification failed or token expired");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            log.error("Token has been invalidated");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
            throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException appException) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid).build();
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission
                            -> stringJoiner.add(permission.getPermissionName()));
                }
            });
        }
        return stringJoiner.toString();
    }
}
