package com.op.boot.mall.client.jingdong;

import com.op.boot.mall.client.MallAuthentication;
import lombok.Data;

/**
 * 京东电商身份认证凭据
 *
 * @author cdrcool
 */
@Data
public class JdMallAuthentication implements MallAuthentication {
    /**
     * App Key
     */
    private String appKey;

    /**
     * App Secret
     */
    private String appSecret;

    /**
     * 访问令牌
     */
    private String accessToken;
}
