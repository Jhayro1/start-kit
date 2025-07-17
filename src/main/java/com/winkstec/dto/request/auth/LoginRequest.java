package com.winkstec.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User login request")
public class LoginRequest {

    @Schema(example = "juan@example.com", description = "User's registered email", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Email
    private String email;

    @Schema(example = "W1nk$tec2024", description = "User password", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String password;
}
