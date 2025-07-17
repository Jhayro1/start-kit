package com.winkstec.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Result of user registration attempt")
public class RegisterResponse {

    @Schema(example = "true", description = "Indicates if registration was successful")
    private boolean success;

    @Schema(example = "OTP_SENT", description = "Code representing the operation result")
    private String code;

    @Schema(example = "OTP sent to email", description = "Human-readable message for frontend")
    private String message;
}
