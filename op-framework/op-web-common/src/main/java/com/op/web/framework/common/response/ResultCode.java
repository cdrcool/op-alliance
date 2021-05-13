package com.op.web.framework.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * 返回码枚举类
 *
 * @author cdrcool
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(HttpServletResponse.SC_OK, "Operation is Successful"),

    /**
     * 操作失败
     */
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "Business Exception"),

    /**
     * 请求读取异常
     */
    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "Message Can't be Read"),

    /**
     * 请求类型不支持异常
     */
    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Supported"),

    /**
     * MIME 类型不支持异常
     */
    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Media Type Not Supported"),

    /**
     * 参数缺少异常
     */
    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "Missing Required Parameter"),

    /**
     * 参数类型异常
     */
    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Type Mismatch"),

    /**
     * 参数绑定异常
     */
    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Binding Error"),

    /**
     * 参数校验异常
     */
    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Validation Error"),

    /**
     * 未授权异常
     */
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "Request Unauthorized"),

    /**
     * 请求拒绝异常
     */
    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "Request Rejected"),

    /**
     * 404 异常
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 Not Found"),

    /**
     * 500 异常
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");

    /**
     * 异常码
     */
    final int code;

    /**
     * 异常描述
     */
    final String desc;
}
