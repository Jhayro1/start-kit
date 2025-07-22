package com.winkstec.success.auth;

import com.winkstec.success.SuccessCodeBase;
import lombok.Getter;

@Getter
public enum AuthSuccessCode implements SuccessCodeBase {

    REGISTER_SUCCESS("If the email exists, an OTP has been sent"),
    REGISTRATION_LINK_SENT("A confirmation link has been sent to your email"),
    OTP_VERIFIED("OTP verified successfully"),
    OTP_RESENT("OTP resent successfully"),
    PROFILE_COMPLETED("Profile completed successfully"),
    LOGIN_SUCCESS("Login successful"),
    REFRESH_SUCCESS("Token refreshed successfully");

    private final String message;

    AuthSuccessCode(String message) {
        this.message = message;
    }
}
