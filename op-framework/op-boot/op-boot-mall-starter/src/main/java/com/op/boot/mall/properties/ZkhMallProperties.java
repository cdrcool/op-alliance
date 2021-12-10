package com.op.boot.mall.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 震坤行配置属性
 *
 * @author cdrcool
 */
@ConfigurationProperties(prefix = "mall.zkh")
@EqualsAndHashCode(callSuper = true)
@Data
public class ZkhMallProperties extends BaseProperties {
    /**
     * 授权地址
     */
    private String authUrl;

    /**
     * 重定向地址
     */
    private String redirectUri;
}
