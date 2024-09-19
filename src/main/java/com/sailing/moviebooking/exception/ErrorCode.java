package com.sailing.moviebooking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9000, "Uncategorized exception.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed.", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1002,"Username must be at least 5 characters.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1003, "Password must be at least 8 characters.", HttpStatus.BAD_REQUEST),
    INVALID_ERROR_KEY(1004, "Invalid message key.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1005, "User does not exist.", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006,"Unauthenticated.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Unauthorized.", HttpStatus.FORBIDDEN),
    INVALID_BIRTHDAY(1008, "Invalid day of birth.", HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
