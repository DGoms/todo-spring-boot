package com.davidgomes.todospringboot.exception;

import org.springframework.http.HttpStatus;

public class BaseHttpException extends RuntimeException {

    private final HttpStatus httpCode;
    private final String code;

    public BaseHttpException(HttpStatus httpCode, String code, String message) {
        super(message);

        this.httpCode = httpCode;
        this.code = code;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public String getCode() {
        return code;
    }
}
