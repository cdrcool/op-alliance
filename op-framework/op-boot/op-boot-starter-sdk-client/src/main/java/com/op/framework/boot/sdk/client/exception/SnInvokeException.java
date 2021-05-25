package com.op.framework.boot.sdk.client.exception;

import lombok.Getter;

/**
 * 苏宁接口调用异常
 *
 * @author cdrcool
 */
@Getter
public class SnInvokeException extends RuntimeException {
    private String errCode;

    public SnInvokeException() {
        super();
    }

    public SnInvokeException(String message) {
        super(message);
    }

    public SnInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnInvokeException(Throwable cause) {
        super(cause);
    }

    protected SnInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SnInvokeException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public SnInvokeException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }
}
