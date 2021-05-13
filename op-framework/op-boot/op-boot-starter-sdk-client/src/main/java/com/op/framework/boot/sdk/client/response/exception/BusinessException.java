package com.op.framework.boot.sdk.client.response.exception;

import com.op.framework.boot.sdk.client.response.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author cdrcool
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    @Getter
    private final ResultCode resultCode;

    public BusinessException() {
        super();
        this.resultCode = ResultCode.FAILURE;
    }

    public BusinessException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.FAILURE;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCode.FAILURE;
    }

    /**
     * @param writableStackTrace 决定是否需要执行 fillInStackTrace，如果不需要关注栈信息，可以设置为 false，以（大幅）提升性能
     */
    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = ResultCode.FAILURE;
    }

    public BusinessException(ResultCode resultCode) {
        super();
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    /**
     * @param writableStackTrace 决定是否需要执行 fillInStackTrace，如果不需要关注栈信息，可以设置为 false，以（大幅）提升性能
     */
    protected BusinessException(ResultCode resultCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = resultCode;
    }
}
