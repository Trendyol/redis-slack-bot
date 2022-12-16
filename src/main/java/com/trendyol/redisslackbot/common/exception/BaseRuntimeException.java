package com.trendyol.redisslackbot.common.exception;

public abstract class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected BaseRuntimeException(String message) {
        super(message);
    }

    public abstract Integer getCode();
}
