package com.winkstec.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Schema(description = "User profile completion data")
public class CompleteProfileRequest {

    @Schema(example = "juan@example.com", description = "Registered email address", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Email
    private String email;

    @Schema(example = "Juan", description = "First name of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String firstName;

    @Schema(example = "Pérez", description = "Father's last name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String lastNameFather;

    @Schema(example = "Gómez", description = "Mother's last name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String lastNameMother;

    @Schema(example = "+51987654321", description = "Phone number in international format", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String phoneNumber;

    @Schema(example = "1995-06-15T00:00:00Z", description = "User's birth date with time zone", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private ZonedDateTime birthday;

    @Schema(example = "America/Lima", description = "User's time zone", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String timezone;
}
