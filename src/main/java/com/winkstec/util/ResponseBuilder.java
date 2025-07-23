package com.winkstec.util;

import com.winkstec.dto.response.common.ApiResponseDto;
import com.winkstec.success.SuccessCodeBase;

/**
 * Utility to create standardized API responses in a clean, consistent way.
 */
public class ResponseBuilder {

    // NUEVOS: con SuccessCodeBase
    public static <T> ApiResponseDto<T> ok(SuccessCodeBase code, T data) {
        return new ApiResponseDto<>(true, code.name(), code.getMessage(), data);
    }

    public static <T> ApiResponseDto<T> ok(SuccessCodeBase code) {
        return new ApiResponseDto<>(true, code.name(), code.getMessage(), null);
    }

    // EXISTENTES: compatibilidad con String
    public static <T> ApiResponseDto<T> ok(String code, String message, T data) {
        return new ApiResponseDto<>(true, code, message, data);
    }

    public static <T> ApiResponseDto<T> ok(String code, String message) {
        return new ApiResponseDto<>(true, code, message, null);
    }

    public static <T> ApiResponseDto<T> error(String code, String message) {
        return new ApiResponseDto<>(false, code, message, null);
    }

    public static <T> ApiResponseDto<T> of(boolean success, String code, String message, T data) {
        return new ApiResponseDto<>(success, code, message, data);
    }
}
