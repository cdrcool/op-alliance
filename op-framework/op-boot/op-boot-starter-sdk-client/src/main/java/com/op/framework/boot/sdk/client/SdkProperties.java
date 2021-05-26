package com.op.framework.boot.sdk.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * SDK 配置属性
 *
 * @author cdrcool
 */
@Data
@ConfigurationProperties(prefix = "sdk")
public class SdkProperties {
    /**
     * 第三方配置属性 Map
     */
    private Map<String, ThirdProperties> accounts = new HashMap<>();

    /**
     * 第三方配置属性
     */
    @Data
    public static class ThirdProperties {
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
         * 定时任务 Cron 表达式
         */
        private Map<String, String> cron;

        /**
         * 请求方式（sdk | feign）
         */
        private String requestType = "sdk";
    }
}
