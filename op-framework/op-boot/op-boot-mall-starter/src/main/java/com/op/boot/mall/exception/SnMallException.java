package com.op.boot.mall.exception;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class SnMallException extends MallException{

    public SnMallException() {
    }

    @Override
    public String getCode() {
        return super.getCode();
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
