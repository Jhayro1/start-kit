package com.winkstec.exception.code;

import com.winkstec.exception.ErrorCodeBase;
import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCodeBase {

    INVALID_CREDENTIALS("Invalid credentials"),
    EMAIL_ALREADY_REGISTERED("Email is already registered"),
    INVALID_OR_EXPIRED_TOKEN("The confirmation link is invalid or has expired"),
    USER_NOT_FOUND("User not found"),
    INVALID_OTP("Invalid or expired verification code"),
    UNAUTHORIZED("Unauthorized request"),
    BAD_REQUEST("Invalid request"),
    FORBIDDEN("Access denied"),
    INTERNAL_ERROR("Internal server error"),
    USER_NOT_VERIFIED("User must verify email before completing profile"),
    USER_ALREADY_VERIFIED("User is already verified");


    private final String message;

    AuthErrorCode(String message) {
        this.message = message;
    }
}
