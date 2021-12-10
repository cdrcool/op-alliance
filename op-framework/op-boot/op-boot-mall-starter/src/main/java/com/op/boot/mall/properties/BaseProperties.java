package com.op.boot.mall.properties;

import lombok.Data;

import java.util.Map;

/**
 * 电商配置属性基类
 *
 * @author chengdr01
 */
@Data
public abstract class BaseProperties {
    /**
     * 服务地址
     */
    private String serverUrl;

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
     * 其他属性
     */
    private Map<String, String> properties;
}
