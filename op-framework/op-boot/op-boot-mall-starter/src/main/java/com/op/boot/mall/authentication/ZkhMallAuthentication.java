package com.op.boot.mall.authentication;

import lombok.Data;

/**
 * 震坤行电商身份认证凭据
 *
 * @author chengdr01
 */
@Data
public class ZkhMallAuthentication implements MallAuthentication {
    /**
     * 访问令牌
     */
    private String accessToken;
}
