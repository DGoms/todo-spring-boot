package com.davidgomes.todospringboot.exception;

public class BaseException extends RuntimeException {

    private final String code;

    public BaseException(String code, String message) {
        super(message);

        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
