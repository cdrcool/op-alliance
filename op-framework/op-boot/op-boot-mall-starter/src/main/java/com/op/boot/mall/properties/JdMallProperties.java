package com.op.boot.mall.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 京东电商配置属性
 *
 * @author cdrcool
 */
@ConfigurationProperties(prefix = "mall.jd")
@EqualsAndHashCode(callSuper = true)
@Data
public class JdMallProperties extends BaseProperties {
    /**
     * 授权地址
     */
    private String authUrl;

    /**
     * 重定向地址
     */
    private String redirectUri;

    /**
     * 请求类型
     */
    private RequestType requestType;
}
