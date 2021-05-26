package com.op.framework.boot.sdk.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * SDK 配置属性
 *
 * @author chengdr01
 */
@Data
@ConfigurationProperties(prefix = "sdk")
public class SdkProperties {
    public static final String REDIS_JD_ACCESS_TOKEN = "s:cl:%s:%s:jd:access:token";
    public static final String REDIS_JD_REFRESH_TOKEN = "s:cl:%s:%s:jd:refresh:token";
    public static final String REDIS_JD_STATE = "s:cl:%s:%s:jd:state";
    public static final String REDIS_SN_ACCESS_TOKEN = "s:cl:%s:%s:sn:access:token";
    public static final String REDIS_SN_REFRESH_TOKEN = "s:cl:%s:%s:sn:refresh:token";
    public static final String REDIS_SN_STATE = "s:cl:%s:%s:sn:state";

    /**
     * 第三方配置属性 Map
     */
    private Map<String, ThirdProperties> third = new HashMap<>();

    /**
     * 第三方配置属性
     */
    @Data
    public static class ThirdProperties {
        /**
         * auth url
         */
        private String authUrl;

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
         * 线上订购应用服务的版本id
         */
        private String itemcode;

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

        /**
         * 重试配置对象
         */
        private SdkProperties.Retry retry = new SdkProperties.Retry();

        /**
         * 其他属性
         */
        private Map<String, String> properties;
    }

    /**
     * 重试配置对象
     */
    @Data
    public static class Retry {
        /**
         * 最大重试次数
         */
        private int maxAttempts = 3;

        /**
         * 重试间隔
         */
        private long delay = 1000;

        /**
         * 延迟倍数
         */
        private double multiplier = 0;

        /**
         * 重试之间最大间隔
         */
        private long maxDelay = 10000;
    }
}
