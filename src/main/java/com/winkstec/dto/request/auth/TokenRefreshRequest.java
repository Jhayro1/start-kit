package com.winkstec.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Refresh token request to renew access token")
public class TokenRefreshRequest {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR...", description = "Valid refresh token", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String refreshToken;
}
