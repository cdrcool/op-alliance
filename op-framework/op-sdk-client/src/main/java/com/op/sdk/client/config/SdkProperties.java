package com.op.sdk.client.config;

import com.op.sdk.client.account.entity.AccountType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * sdk配置属性
 *
 * @author cdrcool
 */
@Data
@Component
@ConfigurationProperties(prefix = "sdk")
public class SdkProperties {
    private Map<AccountType, Account> accounts = new HashMap<>();

    /**
     * 账号配置属性
     */
    @Data
    public static class Account {
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
}
