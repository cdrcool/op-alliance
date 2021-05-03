package com.op.sdk.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 京东帐号配置属性
 *
 * @author cdrcool
 */
@Data
@Component
@ConfigurationProperties(prefix = "jd.account")
public class JdAccountProperties {
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

    /**
     * 定时刷新token cron表达式
     */
    private String refreshTokenCron;
}
