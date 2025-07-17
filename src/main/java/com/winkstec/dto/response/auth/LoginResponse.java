package com.winkstec.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Login result containing access and refresh tokens")
public class LoginResponse {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", description = "JWT access token (short-lived)")
    private String accessToken;

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", description = "JWT refresh token (long-lived)")
    private String refreshToken;

    @Schema(example = "Bearer", description = "Token type used in Authorization header")
    private String tokenType;
}
