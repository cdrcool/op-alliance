package com.op.boot.mall.authentication;

import lombok.Builder;
import lombok.Data;

/**
 * 京东电商金采身份认证凭据
 *
 * @author chengdr01
 */
@Builder
@Data
public class JdMallBillAuthentication implements MallAuthentication {
    /**
     * 访问令牌
     */
    private String accessToken;
}
