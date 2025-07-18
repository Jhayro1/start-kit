package com.winkstec.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final String code;
    private final String message;
    private final String field; // nuevo

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private final ZonedDateTime timestamp;
}
