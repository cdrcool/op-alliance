package com.op.boot.mall.exception;

/**
 * 苏宁电商异常
 *
 * @author chengdr01
 */
public class SnMallException extends MallException {

    public SnMallException() {
    }

    public SnMallException(String message) {
        super(message);
    }

    public SnMallException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnMallException(Throwable cause) {
        super(cause);
    }

    public SnMallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SnMallException(String message, String code) {
        super(message, code);
    }

    public SnMallException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

    public SnMallException(String message, String code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, code, cause, enableSuppression, writableStackTrace);
    }
}
