package com.op.boot.mall.exception;

/**
 * 震坤行电商异常
 *
 * @author chengdr01
 */
public class ZkhMallException extends MallException {

    public ZkhMallException() {
    }

    public ZkhMallException(String message) {
        super(message);
    }

    public ZkhMallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkhMallException(Throwable cause) {
        super(cause);
    }

    public ZkhMallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ZkhMallException(String message, String code) {
        super(message, code);
    }

    public ZkhMallException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

    public ZkhMallException(String message, String code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, code, cause, enableSuppression, writableStackTrace);
    }
}
