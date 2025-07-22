package com.winkstec.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
}
