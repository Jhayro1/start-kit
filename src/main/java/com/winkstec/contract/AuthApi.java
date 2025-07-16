package com.winkstec.contract;

import com.winkstec.dto.request.auth.*;
import com.winkstec.dto.response.auth.*;
import com.winkstec.dto.response.common.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints for user registration, login, OTP verification and token refresh")
@RequestMapping("/auth")
public interface AuthApi {

    @Operation(summary = "Register a new user", description = "Stores registration request and sends confirmation email with token link.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input: weak password or malformed email",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email is already registered",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/request-registration")
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request);

    @Operation(
            summary = "Confirmar registro de usuario",
            description = "Confirma el registro del usuario mediante un token temporal enviado por correo. El token solo es válido por 15 minutos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta activada correctamente"),
            @ApiResponse(responseCode = "400", description = "Token inválido o expirado",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Email ya registrado",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class)))
    })
    @GetMapping("/confirm-registration")
    ResponseEntity<ApiResponseDto<Void>> confirmRegistration(
            @RequestParam("token") String token
    );

    @Operation(summary = "Login with email and password", description = "Authenticates the user and returns a JWT access token and refresh token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or user not verified",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is disabled or blocked",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);


    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Refresh JWT access token", description = "Generates a new access token using a valid refresh token stored in Redis.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/refresh")
    ResponseEntity<TokenRefreshResponse> refresh(@Valid @RequestBody TokenRefreshRequest request);
}
