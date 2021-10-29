package com.op.boot.mall.exception;

/**
 * 京东电商异常
 *
 * @author cdrcool
 */
public class JdMallException extends MallException {
    public JdMallException() {
    }

    @Override
    public String getCode() {
        return super.getCode();
    }

    public JdMallException(String message) {
        super(message);
    }

    public JdMallException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdMallException(Throwable cause) {
        super(cause);
    }

    public JdMallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JdMallException(String message, String code) {
        super(message, code);
    }

    public JdMallException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

    public JdMallException(String message, String code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, code, cause, enableSuppression, writableStackTrace);
    }
}
