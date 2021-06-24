package com.onepiece.gateway.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 白名单配置类
 *
 * @author cdrcool
 */
@Data
@Component
@ConfigurationProperties(prefix = "secure.white-list")
public class WhiteListConfig {
    private List<String> urls;
}
