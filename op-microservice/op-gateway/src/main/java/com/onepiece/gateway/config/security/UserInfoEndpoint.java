package com.onepiece.gateway.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * UserInfo Endpoint
 *
 * @author cdrcool
 */
@RequestMapping("/oauth/userInfo")
@RestController
public class UserInfoEndpoint {

    @GetMapping
    public ApiResponse<Principal> userInfo(Principal principal) {
        return ApiResponse.<Principal>builder().data(principal).build();
    }

    @GetMapping("/currentUser")
    public void getCurrentUser(Authentication authentication) {
    }
}

/**
 * API 返回对象
 *
 * @author cdrcool
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
class ApiResponse<T> {
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


