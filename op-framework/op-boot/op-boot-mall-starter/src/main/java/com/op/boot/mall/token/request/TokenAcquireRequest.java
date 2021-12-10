package com.op.boot.mall.token.request;

import lombok.Data;

/**
 * Token 获取请求
 *
 * @author chengdr01
 */
@Data
public class TokenAcquireRequest {
    /**
     * 账号名
     */
    private String accountName;

    /**
     * 账号密码
     */
    private String password;

    /**
     * 标识字符串
     */
    private String state;

    /**
     * APP Key
     */
    private String appKey;

    /**
     * App Secret
     */
    private String appSecret;

    /**
     * 私钥
     */
    private String privateKey;
}
