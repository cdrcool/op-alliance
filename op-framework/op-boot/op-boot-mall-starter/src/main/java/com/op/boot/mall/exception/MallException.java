package com.op.boot.mall.exception;

import lombok.Getter;

/**
 * 电商异常
 *
 * @author cdrcool
 */
@Getter
public class MallException extends RuntimeException {
    private String code;

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

    public MallException(String message, String code) {
        super(message);
        this.code = code;
    }

    public MallException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public MallException(String message, String code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
