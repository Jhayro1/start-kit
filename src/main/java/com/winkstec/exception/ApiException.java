package com.winkstec.exception;

public class ApiException extends BaseException {
    public ApiException(ErrorCodeBase code) {
        super(code);
    }

    public static ApiException of(ErrorCodeBase code) {
        return new ApiException(code);
    }
}
