package com.onepiece.gateway.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API 返回对象
 *
 * @author cdrcool
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiResponse<T> {
    /**
     * 返回码
     */
    @Builder.Default
    private int code = 200;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 操作是否成功
     *
     * @return true or false
     */
    public boolean isSuccess() {
        return code == 200;
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().code(200).data(data).build();
    }

    public static <T> ApiResponse<T> failure() {
        return ApiResponse.<T>builder().code(500).build();
    }
}
