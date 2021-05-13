package com.op.framework.web.common.api.response.exception;

import com.op.framework.web.common.api.response.ResultCode;
import lombok.Getter;

/**
 * 未授权异常
 *
 * @author cdrcool
 */
public class PermissionDeniedException extends RuntimeException {
    @Getter
    private final ResultCode resultCode;

    public PermissionDeniedException() {
        super();
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }

    public PermissionDeniedException(String message) {
        super(message);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }

    protected PermissionDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }
}
