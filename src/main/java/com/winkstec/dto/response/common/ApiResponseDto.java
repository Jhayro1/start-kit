package com.winkstec.dto.response.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Standard API response structure")
public class ApiResponseDto<T> {

    @Schema(example = "true", description = "Indicates whether the request was successful")
    private boolean success;

    @Schema(example = "USER_PROFILE_UPDATED", description = "Machine-readable code for status or error")
    private String code;

    @Schema(example = "Profile updated successfully", description = "User-friendly message")
    private String message;

    @Schema(description = "Payload containing the actual data of the response")
    private T data;

    public static <T> ApiResponseDto<T> success(T data) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .code("SUCCESS")
                .message("Operación exitosa")
                .data(data)
                .build();
    }

    public static ApiResponseDto<Void> success() {
        return ApiResponseDto.<Void>builder()
                .success(true)
                .code("SUCCESS")
                .message("Operación exitosa")
                .build();
    }

}
