package com.winkstec.dto.request.auth;

import com.winkstec.validation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User registration data")
public class RegisterRequest {

    @Schema(example = "juan@example.com", description = "Email of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(example = "W1nk$tec2024", description = "Strong password (min 8 chars, 1 upper, 1 lower, 1 digit, 1 symbol)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password is required")
    @StrongPassword
    private String password;
}
