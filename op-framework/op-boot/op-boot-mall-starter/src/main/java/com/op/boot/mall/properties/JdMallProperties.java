package com.op.boot.mall.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 京东电商配置属性
 *
 * @author cdrcool
 */
@ConfigurationProperties(prefix = "mall.jd")
@Data
public class JdMallProperties {
    /**
     * 授权地址
     */
    private String authUrl;

    /**
     * 服务地址
     */
    private String serverUrl;

    /**
     * APP Key
     */
    private String appKey;

    /**
     * APP 秘钥
     */
    private String appSecret;

    /**
     * RSA 秘钥
     */
    private String privateKey;

    /**
     * 重定向地址
     */
    private String redirectUri;

    /**
     * 连接超时
     */
    private int connectTimeout;

    /**
     * 读超时
     */
    private int readTimeout;

    /**
     * 超时时最大重试次数
     */
    private int maxAttempts;

    /**
     * 待明确
     */
    private String fuzz;

    /**
     * 默认账号
     */
    private String defaultAccount;

    /**
     * 请求类型
     */
    private RequestType requestType;

    /**
     * cron 表达式
     */
    private Map<String, String> cron;

    /**
     * 其他属性
     */
    private Map<String, String> properties;
}
