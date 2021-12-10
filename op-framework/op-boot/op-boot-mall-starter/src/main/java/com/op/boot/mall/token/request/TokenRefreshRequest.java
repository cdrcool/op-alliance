package com.op.boot.mall.token.request;

import lombok.Data;

/**
 * Token 刷新请求
 *
 * @author chengdr01
 */
@Data
public class TokenRefreshRequest {
    /**
     * 账号名
     */
    private String accountName;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * APP Key
     */
    private String appKey;

    /**
     * App Secret
     */
    private String appSecret;
}
