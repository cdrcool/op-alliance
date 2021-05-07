package com.op.sdk.client.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.sdk.client.response.exception.BusinessException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

/**
 * API 响应通知类，对正常返回值与异常进行统一包装后返回
 *
 * @author cdrcool
 */
@Slf4j
@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;

    public ApiResponseAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getParameterType() != ApiResponse.class
                && AnnotationUtils.findAnnotation(Objects.requireNonNull(methodParameter.getMethod()), NoApiResponse.class) == null
                && AnnotationUtils.findAnnotation(methodParameter.getDeclaringClass(), NoApiResponse.class) == null;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .code(ResultCode.SUCCESS)
                .data(o)
                .build();

        if (o instanceof String) {
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.writeValueAsString(apiResponse);
        } else {
            return apiResponse;
        }
    }

    /**
     * 请求读取异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Object> handleError(HttpMessageNotReadableException e) {
        log.error("Message Not Readable", e);
        return ApiResponse
                .builder()
                .code(ResultCode.MSG_NOT_READABLE)
                .message(e.getMessage())
                .build();
    }

    /**
     * 请求类型不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<Object> handleError(HttpRequestMethodNotSupportedException e) {
        log.error("Request Method Not Supported", e);
        return ApiResponse
                .builder()
                .code(ResultCode.METHOD_NOT_SUPPORTED)
                .message(e.getMessage())
                .build();
    }

    /**
     * MIME 类型不支持异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiResponse<Object> handleError(HttpMediaTypeNotSupportedException e) {
        log.error("Media Type Not Supported", e);
        return ApiResponse
                .builder()
                .code(ResultCode.MEDIA_TYPE_NOT_SUPPORTED)
                .message(e.getMessage())
                .build();
    }

    /**
     * 参数缺少异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<Object> handleError(MissingServletRequestParameterException e) {
        log.warn("Missing Request Parameter", e);
        String message = String.format("Missing Request Parameter: %s", e.getParameterName());
        return ApiResponse
                .builder()
                .code(ResultCode.PARAM_MISS)
                .message(message)
                .build();
    }

    /**
     * 参数类型异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<Object> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("Method Argument Type Mismatch", e);
        String message = String.format("Method Argument Type Mismatch: %s", e.getName());
        return ApiResponse
                .builder()
                .code(ResultCode.PARAM_TYPE_ERROR)
                .message(message)
                .build();
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> handleError(BindException e) {
        log.warn("Bind Exception", e);
        FieldError error = e.getFieldError();
        String message = error != null ? String.format("%s:%s", error.getField(), error.getDefaultMessage()) : "";
        return ApiResponse
                .builder()
                .code(ResultCode.PARAM_BIND_ERROR)
                .message(message)
                .build();
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleError(MethodArgumentNotValidException e) {
        log.warn("Method Argument Not Valid", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String message = error != null ? String.format("%s:%s", error.getField(), error.getDefaultMessage()) : "";
        return ApiResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR)
                .message(message)
                .build();
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Object> handleError(ConstraintViolationException e) {
        log.warn("Constraint Violation", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return ApiResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR)
                .message(message)
                .build();
    }

    /**
     * 404 异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse<Object> handleError(NoHandlerFoundException e) {
        log.error("404 Not Found", e);
        return ApiResponse
                .builder()
                .code(ResultCode.NOT_FOUND)
                .message(e.getMessage())
                .build();
    }

    /**
     * 500 异常
     */
    @ExceptionHandler(Throwable.class)
    public ApiResponse<Object> handleError(Throwable e) {
        log.error("Internal Server Error", e);
        return ApiResponse
                .builder()
                .code(ResultCode.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Object> handleError(BusinessException e) {
        log.error("Business Exception", e);
        return ApiResponse
                .builder()
                .code(e.getResultCode())
                .message(e.getMessage())
                .build();
    }
}
