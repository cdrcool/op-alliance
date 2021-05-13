package com.op.framework.boot.sdk.client.account.exception;

/**
 * 第三方账号异常
 *
 * @author cdrcool
 */
public class ThirdAccountException extends RuntimeException {

    public ThirdAccountException() {
        super();
    }

    public ThirdAccountException(String message) {
        super(message);
    }

    public ThirdAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdAccountException(Throwable cause) {
        super(cause);
    }

    protected ThirdAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
