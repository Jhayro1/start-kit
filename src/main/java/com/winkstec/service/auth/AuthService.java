package com.winkstec.service.auth;

import com.winkstec.dto.request.auth.*;
import com.winkstec.dto.response.auth.*;

public interface AuthService {

    void register(RegisterRequest request);

    void confirmRegistration(String token);

    void completeProfile(CompleteProfileRequest request);

    LoginResponse login(LoginRequest request);

    TokenRefreshResponse refreshToken(TokenRefreshRequest request);
}
