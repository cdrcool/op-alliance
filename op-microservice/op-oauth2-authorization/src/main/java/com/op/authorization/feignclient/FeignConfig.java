package com.op.authorization.feignclient;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 配置类
 *
 * @author cdrcool
 */
@Configuration
public class FeignConfig {

    /**
     * 日志级别
     */
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }
}
