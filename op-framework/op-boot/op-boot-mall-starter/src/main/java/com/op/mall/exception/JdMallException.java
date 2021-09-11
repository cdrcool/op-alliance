package com.op.mall.exception;

/**
 * 京东电商异常
 *
 * @author chengdr01
 */
public class JdMallException extends MallException {
    public JdMallException() {
    }

    public JdMallException(String message, String code) {
        super(message, code);
    }

    public JdMallException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

    public JdMallException(Throwable cause) {
        super(cause);
    }

    public JdMallException(String message, String code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, code, cause, enableSuppression, writableStackTrace);
    }
}
