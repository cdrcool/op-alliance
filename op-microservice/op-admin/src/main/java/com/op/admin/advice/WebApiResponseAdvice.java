package com.op.admin.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.framework.web.common.api.response.ApiResponseAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * API 响应通知类，对正常返回值与异常进行统一包装后返回
 *
 * @author cdrcool
 */
@RestControllerAdvice
public class WebApiResponseAdvice extends ApiResponseAdvice {

    public WebApiResponseAdvice(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
