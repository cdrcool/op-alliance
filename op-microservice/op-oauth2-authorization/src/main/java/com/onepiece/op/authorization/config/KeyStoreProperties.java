package com.onepiece.op.authorization.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 密钥配置属性类
 *
 * @author cdrcool
 */
@Data
@Component
@ConfigurationProperties(prefix = "encrypt.key-store")
public class KeyStoreProperties {
    /**
     * 密钥库位置
     */
    private String location;

    /**
     * 密钥库密码
     */
    private String secret;

    /**
     * 证书别名
     */
    private String alias;

    /**
     * 证书密码
     */
    private String password;
}
