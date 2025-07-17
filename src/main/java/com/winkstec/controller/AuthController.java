package com.winkstec.controller;

import com.winkstec.contract.AuthApi;
import com.winkstec.dto.request.auth.*;
import com.winkstec.dto.response.auth.*;
import com.winkstec.dto.response.common.ApiResponseDto;
import com.winkstec.service.auth.AuthService;
import com.winkstec.success.auth.AuthSuccessCode;
import com.winkstec.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(
                RegisterResponse.builder()
                        .success(true)
                        .code(AuthSuccessCode.REGISTRATION_LINK_SENT.name())
                        .message(AuthSuccessCode.REGISTRATION_LINK_SENT.getMessage())
                        .build()
        );
    }

    /*@Override
    public ResponseEntity<ApiResponseDto<Void>> completeProfile(CompleteProfileRequest request) {
        authService.completeProfile(request);
        return ResponseEntity.ok(ResponseBuilder.ok(AuthSuccessCode.PROFILE_COMPLETED));
    }*/

    @Override
    public ResponseEntity<ApiResponseDto<Void>> confirmRegistration(String token) {
        authService.confirmRegistration(token);
        return ResponseEntity.ok(ApiResponseDto.success());
    }


    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenRefreshResponse> refresh(TokenRefreshRequest request) {
        TokenRefreshResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response); // idem
    }
}
