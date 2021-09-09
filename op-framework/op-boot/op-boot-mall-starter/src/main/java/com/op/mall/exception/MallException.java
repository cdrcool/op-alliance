package com.op.mall.exception;

/**
 * 电商异常
 *
 * @author chengdr01
 */
public class MallException extends RuntimeException {

    public MallException() {
    }

    public MallException(String message) {
        super(message);
    }

    public MallException(String message, Throwable cause) {
        super(message, cause);
    }

    public MallException(Throwable cause) {
        super(cause);
    }

    public MallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
