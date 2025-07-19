package com.winkstec.redis.core;

public enum RedisKeyPrefix {
    OTP("otp"),
    REFRESH_TOKEN("refresh"),
    BLOCKED_TOKEN("revoked"),
    VERIFICATION("verification"),
    REGISTRATION_TOKEN("reg-token");

    private final String prefix;

    RedisKeyPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
