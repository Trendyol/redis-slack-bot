package com.trendyol.redisslackbot.common.exception;

public class BusinessException extends BaseTrendyolException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String exception) {
        super(exception);
    }
}
