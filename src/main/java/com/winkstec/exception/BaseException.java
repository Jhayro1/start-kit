package com.winkstec.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCodeBase code;

    protected BaseException(ErrorCodeBase code) {
        super(code.getMessage());
        this.code = code;
    }
}
