package com.op.boot.mall.authentication;

import lombok.Data;

/**
 * 苏宁电商身份认证凭据
 *
 * @author cdrcool
 */
@Data
public class SnMallAuthentication implements MallAuthentication {
    /**
     * App Key
     */
    private String appKey;

    /**
     * App Secret
     */
    private String appSecret;

    /**
     * 公司子账号
     */
    private String companyCustNo;
}
