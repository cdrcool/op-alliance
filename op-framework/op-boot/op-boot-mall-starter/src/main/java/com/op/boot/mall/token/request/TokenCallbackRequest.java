package com.op.boot.mall.token.request;

import lombok.Data;

/**
 * Token 回调请求
 *
 * @author chengdr01
 */
@Data
public class TokenCallbackRequest {
    /**
     * 账号名
     */
    private String accountName;

    /**
     * 鉴权码
     */
    private String code;

    /**
     * APP Key
     */
    private String appKey;

    /**
     * App Secret
     */
    private String appSecret;
}
