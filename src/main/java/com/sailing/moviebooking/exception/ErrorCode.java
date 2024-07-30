package com.sailing.moviebooking.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9000, "Uncategorized exception."),
    USER_EXISTED(1001, "User existed."),
    INVALID_USERNAME(1002,"Username must be at least 5 characters."),
    INVALID_PASSWORD(1003, "Password must be at least 8 characters."),
    INVALID_ERROR_KEY(1004, "Invalid message key.")
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
