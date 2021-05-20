package com.op.framework.boot.sdk.client.exception;

import lombok.Getter;

/**
 * 京东接口调用异常
 *
 * @author cdrcool
 */
@Getter
public class JdInvokeException extends RuntimeException {
    private String errCode;

    public JdInvokeException() {
        super();
    }

    public JdInvokeException(String message) {
        super(message);
    }

    public JdInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdInvokeException(Throwable cause) {
        super(cause);
    }

    protected JdInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JdInvokeException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public JdInvokeException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }
}
