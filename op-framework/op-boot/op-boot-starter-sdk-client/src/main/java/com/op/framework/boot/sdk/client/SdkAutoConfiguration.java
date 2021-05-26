package com.op.framework.boot.sdk.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * sdk 自动装配类
 *
 * @author cdrcool
 */
@ConditionalOnBean({RedisTemplate.class})
@EnableRetry
@EnableAsync
@EnableScheduling
@EnableFeignClients(basePackages = "com.op.framework.boot.sdk.client.feign")
@MapperScan(basePackages = "com.op.framework.boot.sdk.client.account.mapper")
@EnableConfigurationProperties(SdkProperties.class)
@ComponentScan(basePackageClasses = SdkAutoConfiguration.class)
@Configuration
public class SdkAutoConfiguration {
}
