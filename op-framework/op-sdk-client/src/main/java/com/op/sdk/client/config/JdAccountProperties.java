package com.op.sdk.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 京东帐号配置属性
 *
 * @author chengdr
 */
@Data
@Component
@ConfigurationProperties(prefix = "jd.account")
public class JdAccountProperties implements Cloneable{
    /**
     * server url
     */
    private String serverUrl;

    /**
     * 帐号名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * app key
     */
    private String appKey;

    /**
     * app密钥
     */
    private String appSecret;

    /**
     * 回调地址
     */
    private String redirectUri;

    /**
     * rsa私钥
     */
    private String rsaKey;

    @Override
    public JdAccountProperties clone() {
        try {
            return (JdAccountProperties) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Not support cloneable", e);
        }
    }
}
